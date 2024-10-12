package site.bzyl.dto.req.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 编辑管理员
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTeacherReqDTO {

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 状态
     */
    private String status;
}
