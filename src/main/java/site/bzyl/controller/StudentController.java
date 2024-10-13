package site.bzyl.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import site.bzyl.common.ResponseResult;

import site.bzyl.dto.req.PageUserReqDTO;
import site.bzyl.dto.req.student.AddStudentReqDTO;
import site.bzyl.dto.req.student.UpdateStudentReqDTO;
import site.bzyl.entity.LoginUser;
import site.bzyl.service.StudentService;

import javax.annotation.Resource;

/**
 * 学生接口
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;

    /**
     * 分页查询学生列表
     */
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('management:student')")
    public ResponseResult pageStudent(@RequestBody PageUserReqDTO requestParam) {
        return studentService.pageStudent(requestParam);
    }

    /**
     * 添加学生
     */
    @PostMapping
    @PreAuthorize("hasAuthority('management:student')")
    public ResponseResult addStudent(@RequestBody AddStudentReqDTO requestParam) {
        return studentService.addStudent(requestParam);
    }

    /**
     * 修改学生信息
     */
    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('management:student')")
    public ResponseResult updateStudent(@RequestBody UpdateStudentReqDTO requestParam, @PathVariable Long userId) {
        return studentService.updateStudent(userId, requestParam);
    }

    /**
     * 删除学生
     */
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('management:student')")
    public ResponseResult deleteStudent(@PathVariable Long userId) {
        return studentService.deleteStudent(userId);
    }


    /**
     * 获取学生信息
     */
    @GetMapping("/info")
    @PreAuthorize("hasAnyAuthority('management:student', 'management:individual')")
    public ResponseResult getStudentInfo() {
        LoginUser principal = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = principal.getUser().getUserId();
        return studentService.getStudentInfo(userId);
    }

    /**
     * 修改学生信息
     */
    @PreAuthorize("hasAnyAuthority('management:student', 'management:individual')")
    @PostMapping("/update")
    public ResponseResult updateStudentInfo(@RequestBody UpdateStudentReqDTO requestParam) {
        LoginUser principal = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = principal.getUser().getUserId();
        return studentService.updateStudent(userId, requestParam);
    }


}
