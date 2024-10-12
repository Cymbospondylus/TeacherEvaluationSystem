package site.bzyl.controller;

import org.springframework.web.bind.annotation.*;
import site.bzyl.common.ResponseResult;

import site.bzyl.dto.req.PageUserReqDTO;
import site.bzyl.dto.req.student.AddStudentReqDTO;
import site.bzyl.dto.req.student.UpdateStudentReqDTO;
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
    public ResponseResult pageStudent(@RequestBody PageUserReqDTO requestParam) {
        return studentService.pageStudent(requestParam);
    }

    /**
     * 添加学生
     */
    @PostMapping
    public ResponseResult addStudent(@RequestBody AddStudentReqDTO requestParam) {
        return studentService.addStudent(requestParam);
    }

    /**
     * 修改学生信息
     */
    @PutMapping("/{userId}")
    public ResponseResult updateStudent(@RequestBody UpdateStudentReqDTO requestParam, @PathVariable String userId) {
        return studentService.updateStudent(userId, requestParam);
    }

    /**
     * 删除学生
     */
    @DeleteMapping("/{userId}")
    public ResponseResult deleteStudent(@PathVariable Long userId) {
        return studentService.deleteStudent(userId);
    }
}
