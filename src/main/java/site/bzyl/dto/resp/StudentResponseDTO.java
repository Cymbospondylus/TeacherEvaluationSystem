package site.bzyl.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 学生泪飙响应对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDTO {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 学号，唯一标识学生
     */
    private String studentNumber;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态（0：正常；1：禁用）
     */
    private Integer status;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
