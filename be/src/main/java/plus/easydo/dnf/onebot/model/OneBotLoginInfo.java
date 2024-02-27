package plus.easydo.dnf.onebot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuzhanfeng
 * @Date 2024/2/25
 * @Description 登录信息
 */
@NoArgsConstructor
@Data
public class OneBotLoginInfo {


    @JsonProperty("data")
    private DataDTO data;
    @JsonProperty("message")
    private String message;
    @JsonProperty("retcode")
    private Integer retCode;
    @JsonProperty("status")
    private String status;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("nickname")
        private String nickname;
        @JsonProperty("user_id")
        private Long userId;
    }
}
