package site.bzyl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import site.bzyl.common.ResponseResult;
import site.bzyl.dto.req.*;
import site.bzyl.entity.UserDO;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<UserDO> {
    ResponseResult login(UserLoginRequestDTO requestParam);

    ResponseResult logout();

    ResponseResult getUserInfo(HttpServletRequest request);

    ResponseResult pageUserByRole(PageUserReqDTO requestParam);

    ResponseResult addAdmin(AddAdminReqDTO requestParam);

    ResponseResult updateAdmin(Long userId, UpdateAdminReqDTO requestParam);

    ResponseResult deleteAdmin(Long userId);

    ResponseResult updatePassword(UpdatePasswordReqDTO requestParam);
}
