package site.bzyl.controller;

import org.springframework.web.bind.annotation.*;
import site.bzyl.common.ResponseResult;
import site.bzyl.dto.req.admin.AddAdminReqDTO;
import site.bzyl.dto.req.PageUserReqDTO;
import site.bzyl.dto.req.admin.UpdateAdminReqDTO;
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

    @PostMapping("/user/list")
    public ResponseResult pageUserByRole(@RequestBody PageUserReqDTO requestParam) {
        return userService.pageUserByRole(requestParam);
    }

    @PostMapping
    public ResponseResult addAdmin(@RequestBody AddAdminReqDTO requestParam) {
        return userService.addAdmin(requestParam);
    }

    @PutMapping("/{userId}")
    public ResponseResult updateAdmin(@PathVariable Long userId, @RequestBody UpdateAdminReqDTO requestParam) {
        return userService.updateAdmin(userId, requestParam);
    }

    @DeleteMapping("/{userId}")
    public ResponseResult deleteAdmin(@PathVariable Long userId) {
        return userService.deleteAdmin(userId);
    }
}
