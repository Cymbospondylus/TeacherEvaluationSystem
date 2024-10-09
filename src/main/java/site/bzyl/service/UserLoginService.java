package site.bzyl.service;

import site.bzyl.common.ResponseResult;
import site.bzyl.dto.UserLoginRequestDTO;

public interface UserLoginService {
    ResponseResult login(UserLoginRequestDTO requestParam);

    ResponseResult logout();
}
