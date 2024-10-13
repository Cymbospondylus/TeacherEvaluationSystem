package site.bzyl.common.enums;


import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN(1L, "admin"),
    STUDENT(2L, "student");

    private final Long roleId;
    private final String name;

    UserRole(Long roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }
}
