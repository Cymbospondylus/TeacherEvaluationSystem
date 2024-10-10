package site.bzyl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoResponseDTO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;
}
