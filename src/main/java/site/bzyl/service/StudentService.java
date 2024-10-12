package site.bzyl.service;

import site.bzyl.common.ResponseResult;
import site.bzyl.dto.req.PageUserReqDTO;
import site.bzyl.dto.req.student.AddStudentReqDTO;
import site.bzyl.dto.req.student.UpdateStudentReqDTO;

public interface StudentService {
    ResponseResult pageStudent(PageUserReqDTO requestParam);

    ResponseResult deleteStudent(Long userId);

    ResponseResult addStudent(AddStudentReqDTO requestParam);

    ResponseResult updateStudent(String userId, UpdateStudentReqDTO requestParam);
}
