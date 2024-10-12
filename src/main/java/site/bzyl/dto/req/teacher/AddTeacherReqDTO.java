package site.bzyl.dto.req.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 新增教师
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddTeacherReqDTO {
    /**
     * 教师姓名
     */
    @NotBlank(message = "用户名不能为空")
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;
}
