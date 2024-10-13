package site.bzyl.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@SpringBootTest
public class EvaluationMapperTest {
    @Resource
    private EvaluationMapper evaluationMapper;

    @Test
    public void testGetEvaluation() {
        Map<String, BigDecimal> evaluationResult = evaluationMapper.getEvaluationResult(3L);
        System.out.println(evaluationResult);
    }
}
