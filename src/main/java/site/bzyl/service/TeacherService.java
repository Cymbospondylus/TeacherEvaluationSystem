package site.bzyl.service;

import site.bzyl.common.ResponseResult;
import site.bzyl.dto.req.teacher.AddTeacherReqDTO;
import site.bzyl.dto.req.teacher.PageTeacherReqDTO;
import site.bzyl.dto.req.teacher.UpdateTeacherReqDTO;

public interface TeacherService {
    ResponseResult pageTeacherList(PageTeacherReqDTO requestParam);

    ResponseResult addTeacher(AddTeacherReqDTO requestParam);

    ResponseResult updateTeacher(Long teacherId, UpdateTeacherReqDTO requestParam);

    ResponseResult deleteTeacher(Long teacherId);
}
