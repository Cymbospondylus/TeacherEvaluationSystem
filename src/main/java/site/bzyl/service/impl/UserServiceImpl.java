package site.bzyl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bzyl.common.RedisConstant;
import site.bzyl.common.ResponseResult;
import site.bzyl.common.UserConstant;
import site.bzyl.common.enums.UserRole;
import site.bzyl.dto.req.*;
import site.bzyl.dto.resp.PageUserRespDTO;
import site.bzyl.dto.resp.UserInfoResponseDTO;
import site.bzyl.entity.LoginUser;
import site.bzyl.entity.SysUserRoleDO;
import site.bzyl.entity.UserDO;
import site.bzyl.mapper.UserMapper;
import site.bzyl.mapper.UserRoleMapper;
import site.bzyl.service.UserService;
import site.bzyl.util.JwtUtil;
import site.bzyl.util.RedisCache;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserRoleMapper userRoleMapper;



    @Override
    public ResponseResult login(UserLoginRequestDTO requestParam) {
        // AuthenticationManager.authenticate进行用户认证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(requestParam.getUsername(), requestParam.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        // 认证通过, 使用 userId 生成 jwt
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getUserId().toString();
        String jwt = JwtUtil.createJWT(userId);
        // 会话信息放入 redis
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("token", jwt);
        redisCache.setCacheObject(RedisConstant.LOGIN_TOKEN_PREFIX + userId, loginUser, 30, TimeUnit.MINUTES);
        return ResponseResult.success(resultMap);
    }

    @Override
    public ResponseResult logout() {
        // 获取 JwtToken 拦截器中存入 securityContextHolder 的用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getUserId();
        // 删除 Redis 中的会话信息
        redisCache.deleteObject(RedisConstant.LOGIN_TOKEN_PREFIX + userId);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult getUserInfo(HttpServletRequest request) {
        // 获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        UserDO user = loginUser.getUser();
        // 封装成InfoDTO返回
        UserInfoResponseDTO userInfoResponseDTO = UserInfoResponseDTO.builder()
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .role("admin")
                .build();
        return ResponseResult.success(userInfoResponseDTO);
    }

    @Override
    public ResponseResult pageUserByRole(PageUserReqDTO requestParam) {
        List<PageUserRespDTO> userResult = baseMapper.selectPageByRoleId(requestParam)
                .stream()
                .map(each -> PageUserRespDTO.builder()
                        .userId(each.getUserId())
                        .phone(each.getPhone())
                        .email(each.getEmail())
                        .status(each.getStatus())
                        .username(each.getUsername())
                        .build())
                .collect(Collectors.toList());
        return ResponseResult.success(userResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addAdmin(AddAdminReqDTO requestParam) {
        UserDO userDO = baseMapper.selectOne(Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername()));
        if (userDO != null) {
            throw new RuntimeException("用户名已存在");
        }
        // 添加管理员用户
        userDO = UserDO.builder()
                .username(requestParam.getUsername())
                .password(passwordEncoder.encode(requestParam.getPassword()))
                .phone(requestParam.getPhone())
                .email(requestParam.getEmail())
                .avatar(UserConstant.DEFAULT_AVATAR)
                .build();
        baseMapper.insert(userDO);
        // 添加管理员角色
        SysUserRoleDO userRoleDO = SysUserRoleDO.builder()
                .userId(userDO.getUserId())
                .roleId(UserRole.ADMIN.getRoleId())
                .build();
        userRoleMapper.insert(userRoleDO);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateAdmin(Long userId, UpdateAdminReqDTO requestParam) {
        UserDO userDO = baseMapper.selectById(userId);
        userDO.setEmail(requestParam.getEmail())
                .setPhone(requestParam.getPhone())
                .setStatus(requestParam.getStatus().equals("enabled") ? 0 : 1);
        baseMapper.updateById(userDO);
        return ResponseResult.success();
    }

    @Override
    @Transactional
    public ResponseResult deleteAdmin(Long userId) {
        // 删除用户信息
        baseMapper.deleteById(userId);
        // 删除角色信息
        userRoleMapper.delete(Wrappers.lambdaQuery(SysUserRoleDO.class).eq(SysUserRoleDO::getUserId, userId));
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updatePassword(UpdatePasswordReqDTO requestParam) {
        // 获取登陆用户信息
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 获取登录用户密码
        String password = loginUser.getUser().getPassword();
        // 比较输入的旧密码和数据库的旧密码是否相同
        if (!passwordEncoder.matches(requestParam.getOldPassword(), password)) {
            throw new RuntimeException("旧密码输入错误");
        }
        // 修改为新的密码
        UserDO userDO = loginUser.getUser().setPassword(passwordEncoder.encode(requestParam.getNewPassword()));
        baseMapper.updateById(userDO);
        return ResponseResult.success();
    }
}
