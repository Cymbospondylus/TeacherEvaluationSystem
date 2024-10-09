package site.bzyl.eneity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("tb_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDO extends BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户角色（0：管理员；1：学生）
     */
    private Integer role;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;


}
