package site.bzyl.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页查询用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageUserReqDTO {
    /**
     * 角色Id
     */
    private Long roleId;

    /**
     * 每页显示条数，默认 10
     */
    private int size = 10;

    /**
     * 当前页
     */
    private int current = 1;
}
