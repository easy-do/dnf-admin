package plus.easydo.dnf.onebot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuzhanfeng
 * @Date 2024/2/25
 * @Description 发送人信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OneBotSender {

    private Integer age;
    private String area;
    private String card;
    private String level;
    private String nickname;
    private String role;
    private String sex;
    private String title;
    @JsonProperty("user_id")
    private Integer userId;
}
