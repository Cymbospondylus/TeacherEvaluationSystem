package site.bzyl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户角色表
 */
@Data
@TableName("sys_user_role")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRoleDO {
    /**
     * 用户Id
     */
    private Long userId;

    private Long roleId;
}
