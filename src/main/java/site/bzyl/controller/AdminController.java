package site.bzyl.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.bzyl.common.ResponseResult;
import site.bzyl.dto.req.PageUserReqDTO;
import site.bzyl.service.UserService;

import javax.annotation.Resource;

/**
 * 管理员接口
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private UserService userService;

    @GetMapping("/user/list")
    public ResponseResult pageUserByRole(@RequestBody PageUserReqDTO requestParam) {
        return userService.pageUserByRole(requestParam);
    }
}
