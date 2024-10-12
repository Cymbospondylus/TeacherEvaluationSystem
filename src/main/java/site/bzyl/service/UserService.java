package site.bzyl.service;

import site.bzyl.common.ResponseResult;
import site.bzyl.dto.req.*;
import site.bzyl.dto.req.admin.AddAdminReqDTO;
import site.bzyl.dto.req.admin.UpdateAdminReqDTO;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    ResponseResult login(UserLoginRequestDTO requestParam);

    ResponseResult logout();

    ResponseResult getUserInfo(HttpServletRequest request);

    ResponseResult pageUserByRole(PageUserReqDTO requestParam);

    ResponseResult addAdmin(AddAdminReqDTO requestParam);

    ResponseResult updateAdmin(Long userId, UpdateAdminReqDTO requestParam);

    ResponseResult deleteAdmin(Long userId);

    ResponseResult updatePassword(UpdatePasswordReqDTO requestParam);
}
