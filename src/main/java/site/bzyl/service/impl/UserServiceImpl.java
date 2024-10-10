package site.bzyl.service.impl;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import site.bzyl.common.RedisConstant;
import site.bzyl.common.ResponseResult;
import site.bzyl.dto.UserInfoResponseDTO;
import site.bzyl.dto.UserLoginRequestDTO;
import site.bzyl.eneity.LoginUser;
import site.bzyl.eneity.UserDO;
import site.bzyl.mapper.UserMapper;
import site.bzyl.service.UserService;
import site.bzyl.util.JwtUtil;
import site.bzyl.util.RedisCache;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
        redisCache.setCacheObject(RedisConstant.LOGIN_TOKEN_PREFIX + userId, loginUser, 30, TimeUnit.SECONDS);
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
        //解析token
        String token = request.getHeader("token");
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        //从redis中获取用户信息
        String redisKey = RedisConstant.LOGIN_TOKEN_PREFIX + userId;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        UserDO user = loginUser.getUser();
        // 封装成InfoDTO返回
        UserInfoResponseDTO userInfoResponseDTO = UserInfoResponseDTO.builder()
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .build();
        return ResponseResult.success(userInfoResponseDTO);
    }
}
