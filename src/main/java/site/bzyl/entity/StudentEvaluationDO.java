package site.bzyl.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 学生评价记录实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_student_evaluation")
public class StudentEvaluationDO {

    @TableId(type = IdType.AUTO)
    private Long evaluationId;

    private Long userId;

    private Long teacherId;

    private Long indicatorId;

    private Integer score;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
