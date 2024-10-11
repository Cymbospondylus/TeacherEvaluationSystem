package site.bzyl.controller;

import org.springframework.web.bind.annotation.*;
import site.bzyl.common.ResponseResult;
import site.bzyl.dto.req.UpdatePasswordReqDTO;
import site.bzyl.dto.req.UserLoginRequestDTO;
import site.bzyl.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserLoginRequestDTO requestParam) {
        return userService.login(requestParam);
    }

    @GetMapping("/logout")
    public ResponseResult logout() {
        return userService.logout();
    }

    @GetMapping("/info")
    public ResponseResult getUserInfo(HttpServletRequest request) {
        return userService.getUserInfo(request);
    }

    @PutMapping("/updatePassword")
    public ResponseResult updatePassword(@RequestBody UpdatePasswordReqDTO requestParam) {
        return userService.updatePassword(requestParam);
    }


}
