package site.bzyl.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import site.bzyl.common.RedisConstant;
import site.bzyl.common.ResponseResult;
import site.bzyl.dto.UserLoginRequestDTO;
import site.bzyl.eneity.LoginUser;
import site.bzyl.service.UserLoginService;
import site.bzyl.util.JwtUtil;
import site.bzyl.util.RedisCache;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserLoginServiceImpl implements UserLoginService {
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
        // 认证不通过，抛出异常
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登陆失败");
        }
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
}
