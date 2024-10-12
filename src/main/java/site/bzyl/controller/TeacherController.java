package site.bzyl.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.bzyl.common.ResponseResult;
import site.bzyl.dto.req.teacher.AddTeacherReqDTO;
import site.bzyl.dto.req.teacher.PageTeacherReqDTO;
import site.bzyl.dto.req.teacher.UpdateTeacherReqDTO;
import site.bzyl.service.TeacherService;

import javax.annotation.Resource;

/**
 * 教师管理接口
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    /**
     * 分页获取教师列表
     */
    @PostMapping("/list")
    public ResponseResult pageTeacherList(@RequestBody PageTeacherReqDTO requestParam) {
        return teacherService.pageTeacherList(requestParam);
    }

    /**
     * 添加教师
     */
    @PostMapping
    public ResponseResult addTeacher(@Validated @RequestBody AddTeacherReqDTO requestParam) {
        return teacherService.addTeacher(requestParam);
    }

    /**
     * 更新教师信息
     */
    @PutMapping("/{teacherId}")
    public ResponseResult updateTeacher(@PathVariable Long teacherId, @RequestBody UpdateTeacherReqDTO requestParam) {
        return teacherService.updateTeacher(teacherId, requestParam);
    }

    /**
     * 删除教师
     */
    @DeleteMapping("/{teacherId}")
    public ResponseResult deleteTeacher(@PathVariable Long teacherId) {
        return teacherService.deleteTeacher(teacherId);
    }
}
