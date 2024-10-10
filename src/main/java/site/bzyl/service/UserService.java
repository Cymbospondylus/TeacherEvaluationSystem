package site.bzyl.service;

import com.baomidou.mybatisplus.service.IService;
import site.bzyl.common.ResponseResult;
import site.bzyl.dto.UserLoginRequestDTO;
import site.bzyl.eneity.UserDO;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<UserDO> {
    ResponseResult login(UserLoginRequestDTO requestParam);

    ResponseResult logout();

    ResponseResult getUserInfo(HttpServletRequest request);
}
