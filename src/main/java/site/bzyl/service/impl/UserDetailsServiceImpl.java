package site.bzyl.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.bzyl.entity.LoginUser;
import site.bzyl.entity.UserDO;
import site.bzyl.mapper.MenuMapper;
import site.bzyl.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 自定义UserDetailService实现类, 通过数据库用户认证
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 根据用户名查登陆用户
        UserDO userDO = userMapper.selectOne(Wrappers.lambdaQuery(UserDO.class).eq(UserDO::getUsername, s));
        if (Objects.isNull(userDO)) {
            throw new RuntimeException("用户名不存在");
        }
        // 根据用户Id查询权限
        List<String> perms = menuMapper.selectPermsByUserId(userDO.getUserId());
        return LoginUser.builder()
                .user(userDO)
                .permissions(perms)
                .build();
    }
}
