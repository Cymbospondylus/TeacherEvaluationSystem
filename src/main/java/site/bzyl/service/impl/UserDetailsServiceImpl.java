package site.bzyl.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.bzyl.eneity.LoginUser;
import site.bzyl.eneity.UserDO;
import site.bzyl.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 根据用户名查登陆用户
        UserDO userDO = UserDO.builder()
                .username(s)
                .build();
        userDO = userMapper.selectOne(userDO);
        if (Objects.isNull(userDO)) {
            throw new RuntimeException("用户名密码错误");
        }

        return LoginUser.builder()
                .user(userDO)
                .build();
    }
}
