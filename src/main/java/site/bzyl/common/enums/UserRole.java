package site.bzyl.common.enums;

import lombok.Data;

/**
 * 用户角色枚举
 */

public enum UserRole {
    /**
     * 管理员
     */
    ADMIN(1L),

    /**
     * 学生
     */
    STUDENT(2L);

    private Long roleId;

    UserRole(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }
}
