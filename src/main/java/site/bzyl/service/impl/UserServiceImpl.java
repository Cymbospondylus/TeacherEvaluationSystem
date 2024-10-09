package site.bzyl.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import site.bzyl.eneity.UserDO;
import site.bzyl.mapper.UserMapper;
import site.bzyl.service.UserService;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    @Resource
    private UserMapper userMapper;
}
