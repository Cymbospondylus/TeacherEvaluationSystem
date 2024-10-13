package site.bzyl.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bzyl.common.ResponseResult;
import site.bzyl.common.UserConstant;
import site.bzyl.common.enums.UserRole;
import site.bzyl.dto.req.PageUserReqDTO;
import site.bzyl.dto.req.student.AddStudentReqDTO;
import site.bzyl.dto.req.student.UpdateStudentReqDTO;
import site.bzyl.dto.resp.StudentResponseDTO;
import site.bzyl.entity.StudentDO;
import site.bzyl.entity.SysUserRoleDO;
import site.bzyl.entity.UserDO;
import site.bzyl.mapper.StudentMapper;
import site.bzyl.mapper.UserMapper;
import site.bzyl.mapper.UserRoleMapper;
import site.bzyl.service.StudentService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentMapper studentMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public ResponseResult pageStudent(PageUserReqDTO requestParam) {
        List<StudentResponseDTO> studentDTOS = studentMapper.pageStudent(requestParam);
        return ResponseResult.success(studentDTOS);
    }

    @Override
    @Transactional
    public ResponseResult deleteStudent(Long userId) {
        userMapper.deleteById(userId);
        studentMapper.delete(Wrappers.lambdaQuery(StudentDO.class).eq(StudentDO::getUserId, userId));
        return ResponseResult.success();
    }

    @Override
    @Transactional
    public ResponseResult addStudent(AddStudentReqDTO requestParam) {
        UserDO userDO = userMapper.selectOne(Wrappers.lambdaQuery(UserDO.class).eq(UserDO::getUsername, requestParam.getUsername()));
        if (userDO != null) {
            throw new RuntimeException("用户名已存在");
        }
        // 保存用户信息
         userDO = UserDO.builder()
                .username(requestParam.getUsername())
                .avatar(UserConstant.DEFAULT_AVATAR)
                .email(requestParam.getEmail())
                .phone(requestParam.getPhone())
                .password(passwordEncoder.encode(UserConstant.DEFAULT_PASSWORD))
                .build();
        userMapper.insert(userDO);

        // 保存学生信息
        StudentDO studentDO = StudentDO.builder()
                .studentNumber(requestParam.getStudentNumber())
                .name(requestParam.getName())
                .userId(userDO.getUserId())
                .build();
        studentMapper.insert(studentDO);

        // 保存角色和权限信息
        SysUserRoleDO userRoleDO = SysUserRoleDO.builder()
                .roleId(UserRole.STUDENT.getRoleId())
                .userId(userDO.getUserId())
                .build();
        userRoleMapper.insert(userRoleDO);
        return ResponseResult.success();
    }

    @Override
    @Transactional
    public ResponseResult updateStudent(Long userId, UpdateStudentReqDTO requestParam) {
        UserDO userDO = userMapper.selectById(userId);
        userDO.setEmail(requestParam.getEmail())
                .setPhone(requestParam.getPhone());
        userMapper.updateById(userDO);
        StudentDO studentDO = studentMapper.selectOne(Wrappers.lambdaQuery(StudentDO.class).eq(StudentDO::getUserId, userId));
        studentDO.setStudentNumber(requestParam.getStudentNumber())
                .setName(requestParam.getName());
        studentMapper.updateById(studentDO);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult getStudentInfo(Long userId) {
        UserDO userDO = userMapper.selectById(userId);
        StudentDO studentDO = studentMapper.selectOne(Wrappers.lambdaQuery(StudentDO.class).eq(StudentDO::getUserId, userId));
        return ResponseResult.success(StudentResponseDTO.builder()
                        .userId(userDO.getUserId())
                        .username(userDO.getUsername())
                .userId(userDO.getUserId())
                .studentNumber(studentDO.getStudentNumber())
                .name(studentDO.getName())
                .createTime(userDO.getCreateTime())
                .updateTime(userDO.getUpdateTime())
                .status(userDO.getStatus())
                .avatar(userDO.getAvatar())
                .email(userDO.getEmail())
                .phone(userDO.getPhone())
                .build());
    }
}
