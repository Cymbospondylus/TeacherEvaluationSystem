package site.bzyl.controller;

import org.springframework.web.bind.annotation.*;
import site.bzyl.common.ResponseResult;
import site.bzyl.dto.req.evaluation.EvaluationReqDTO;
import site.bzyl.service.EvaluationService;

import javax.annotation.Resource;

/**
 * 学生评价教师接口
 */
@RestController
@RequestMapping("/evaluation")
public class EvaluationController {

    @Resource
    private EvaluationService evaluationService;

    @GetMapping("/teachers")
    public ResponseResult listTeachersForEvaluation() {
        return evaluationService.listTeachersForEvaluation();
    }

    @GetMapping("/indicators")
    public ResponseResult listIndicators() {
        return evaluationService.listIndicators();
    }

    @PostMapping("/submit")
    public ResponseResult submitEvaluation(@RequestBody EvaluationReqDTO evaluationReqDTO) {
        return evaluationService.submitEvaluation(evaluationReqDTO);
    }

    @GetMapping("/results/{teacherId}")
    public ResponseResult getTeacherEvaluationResults(@PathVariable Long teacherId) {
        return evaluationService.getTeacherEvaluationResults(teacherId);
    }

}
