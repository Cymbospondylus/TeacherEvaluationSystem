package site.bzyl.controller;

import org.springframework.web.bind.annotation.*;
import site.bzyl.common.ResponseResult;
import site.bzyl.dto.UserLoginRequestDTO;
import site.bzyl.service.UserLoginService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserLoginController {

    @Resource
    private UserLoginService userLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserLoginRequestDTO requestParam) {
        return userLoginService.login(requestParam);
    }

    @GetMapping("/logout")
    public ResponseResult logout() {
        return userLoginService.logout();
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
