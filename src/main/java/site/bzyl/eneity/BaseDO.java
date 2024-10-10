package site.bzyl.eneity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDO {
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除（0:未删除;1:已删除）
     */
    private Integer del_flag;
}
