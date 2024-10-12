package site.bzyl.dto.req.admin;

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
public class UpdateAdminReqDTO {

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
