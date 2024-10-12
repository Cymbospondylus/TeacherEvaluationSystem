package site.bzyl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@TableName("tb_teacher")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TeacherDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /**
     * 教师ID
     */
    private Long teacherId;

    /**
     * 教师姓名
     */
    private String name;

    /**
     * 状态（0：在职；1：离职）
     */
    private Integer status;

    /**
     * 教师邮箱
     */
    private String email;

    /**
     * 教师联系电话
     */
    private String phone;

}
