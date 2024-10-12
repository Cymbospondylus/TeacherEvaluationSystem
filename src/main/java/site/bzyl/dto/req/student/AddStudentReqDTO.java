package site.bzyl.dto.req.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 新增学生
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddStudentReqDTO {
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
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;
}
