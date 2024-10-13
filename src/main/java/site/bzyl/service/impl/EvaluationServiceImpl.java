package site.bzyl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import site.bzyl.common.ResponseResult;
import site.bzyl.dto.req.evaluation.EvaluationReqDTO;
import site.bzyl.entity.LoginUser;
import site.bzyl.entity.StudentEvaluationDO;
import site.bzyl.entity.IndicatorDO;
import site.bzyl.entity.TeacherDO;
import site.bzyl.mapper.EvaluationMapper;
import site.bzyl.mapper.TeacherMapper;
import site.bzyl.service.EvaluationService;
import site.bzyl.service.TeacherService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private EvaluationMapper evaluationMapper;

    @Override
    public ResponseResult listTeachersForEvaluation() {
        List<TeacherDO> teachers = teacherMapper.selectList(null);
        return ResponseResult.success(teachers);
    }

    @Override
    public ResponseResult listIndicators() {
        // 获取所有评价指标
        List<IndicatorDO> indicators = evaluationMapper.listIndicators();
        return ResponseResult.success(indicators);
    }

    @Override
    public ResponseResult submitEvaluation(EvaluationReqDTO evaluationReqDTO) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getUserId();
        // 创建评价记录
        StudentEvaluationDO evaluation = StudentEvaluationDO.builder()
                .userId(userId)
                .teacherId(evaluationReqDTO.getTeacherId())
                .indicatorId(evaluationReqDTO.getIndicatorId())
                .score(evaluationReqDTO.getScore())
                .build();

        // 插入到数据库
        int result = evaluationMapper.insert(evaluation);
        if (result > 0) {
            return ResponseResult.success("评价提交成功");
        }
        return ResponseResult.error("评价提交失败");
    }

    @Override
    public ResponseResult getTeacherEvaluationResults(Long teacherId) {
        // 调用 EvaluationMapper 的方法，获取指定教师的评价结果
        Map<String, BigDecimal> evaluationResult = evaluationMapper.getEvaluationResult(teacherId);
        return ResponseResult.success(evaluationResult);
    }
}
