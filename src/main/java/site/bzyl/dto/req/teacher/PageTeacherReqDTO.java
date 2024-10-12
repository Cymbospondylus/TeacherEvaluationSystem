package site.bzyl.dto.req.teacher;

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
public class PageTeacherReqDTO {

    /**
     * 每页显示条数，默认 10
     */
    private int size = 10;

    /**
     * 当前页
     */
    private int current = 1;
}
