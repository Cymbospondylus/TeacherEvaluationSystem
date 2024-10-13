package site.bzyl.service;

import site.bzyl.common.ResponseResult;
import site.bzyl.dto.req.evaluation.EvaluationReqDTO;

public interface EvaluationService {
    ResponseResult listTeachersForEvaluation();
    ResponseResult listIndicators();
    ResponseResult submitEvaluation(EvaluationReqDTO evaluationReqDTO);

    ResponseResult getTeacherEvaluationResults(Long teacherId);
}
