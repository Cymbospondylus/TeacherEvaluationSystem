package site.bzyl.dto.req.evaluation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationReqDTO {
    private Long teacherId;
    private Long indicatorId;
    private Integer score;
    private String comment;
}
