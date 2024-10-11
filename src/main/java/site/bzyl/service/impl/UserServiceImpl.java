package site.bzyl.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import site.bzyl.common.RedisConstant;
import site.bzyl.common.ResponseResult;
import site.bzyl.dto.req.PageUserReqDTO;
import site.bzyl.dto.resp.PageUserRespDTO;
import site.bzyl.dto.resp.UserInfoResponseDTO;
import site.bzyl.dto.req.UserLoginRequestDTO;
import site.bzyl.entity.LoginUser;
import site.bzyl.entity.UserDO;
import site.bzyl.mapper.UserMapper;
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
}
