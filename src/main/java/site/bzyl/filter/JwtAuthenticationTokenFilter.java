package site.bzyl.filter;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import site.bzyl.common.RedisConstant;
import site.bzyl.eneity.LoginUser;
import site.bzyl.util.JwtUtil;
import site.bzyl.util.RedisCache;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Token认证过滤器
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        //如果还没有token，说明是初次请求，没登录，直接放行去登录即可
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
            //这里要return是因为响应回来的时候还会回到doFilter方法的下一行
            return;
        }
        //解析token
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"message\": \"" + "token非法" + "\", \"status\": 401}");
            return;
        }
        LoginUser loginUser = null;

        //从redis中获取用户信息
        String redisKey = RedisConstant.LOGIN_TOKEN_PREFIX + userId;
        loginUser = redisCache.getCacheObject(redisKey);
        if (Objects.isNull(loginUser)) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"message\": \"" + "用户未登录" + "\", \"status\": 401}");
            return;
        }

        //存入SecurityContextHolder，给后面的过滤器确定认证状态
        Authentication authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行请求
        filterChain.doFilter(request, response);
    }
}