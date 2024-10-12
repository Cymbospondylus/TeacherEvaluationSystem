package site.bzyl.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import site.bzyl.common.ResponseResult;
import site.bzyl.dto.req.teacher.AddTeacherReqDTO;
import site.bzyl.dto.req.teacher.PageTeacherReqDTO;
import site.bzyl.dto.req.teacher.UpdateTeacherReqDTO;
import site.bzyl.entity.TeacherDO;
import site.bzyl.mapper.TeacherMapper;
import site.bzyl.service.TeacherService;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, TeacherDO> implements TeacherService {
    @Override
    public ResponseResult pageTeacherList(PageTeacherReqDTO requestParam) {
        Page<TeacherDO> teacherDOPage = new Page<>(requestParam.getCurrent(), requestParam.getSize());
        baseMapper.selectPage(teacherDOPage, null);
        return ResponseResult.success(teacherDOPage);
    }

    @Override
    public ResponseResult addTeacher(AddTeacherReqDTO requestParam) {
        TeacherDO teacherDO = TeacherDO.builder()
                .name(requestParam.getName())
                .phone(requestParam.getPhone())
                .email(requestParam.getEmail())
                .build();
        baseMapper.insert(teacherDO);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateTeacher(Long teacherId, UpdateTeacherReqDTO requestParam) {
        TeacherDO teacherDO = baseMapper.selectById(teacherId);
        if (teacherDO == null) {
            throw new RuntimeException("该教师不存在");
        }
        teacherDO.setPhone(requestParam.getPhone())
                .setEmail(requestParam.getEmail());
        baseMapper.updateById(teacherDO);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult deleteTeacher(Long teacherId) {
        baseMapper.deleteById(teacherId);
        return ResponseResult.success();
    }
}
