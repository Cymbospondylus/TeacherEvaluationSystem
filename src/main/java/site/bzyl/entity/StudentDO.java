package site.bzyl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@TableName("tb_student")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class StudentDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 学生ID
     */
    @TableId(type = IdType.AUTO)
    private Long studentId;

    /**
     * 用户ID (外键，关联 tb_user 表)
     */
    private Long userId;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 学号，唯一标识学生
     */
    @TableField("student_number")
    private String studentNumber;
}
