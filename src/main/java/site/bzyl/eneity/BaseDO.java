package site.bzyl.eneity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDO {
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    private LocalDateTime updatedTime;
}
