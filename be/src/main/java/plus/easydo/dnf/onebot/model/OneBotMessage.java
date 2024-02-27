package plus.easydo.dnf.onebot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description OntBot消息参数封装
 * @date 2024/2/25
 */

@NoArgsConstructor
@Data
public class OneBotMessage {


    @JsonProperty("data")
    private MessageData data;
    @JsonProperty("type")
    private String type;

    @NoArgsConstructor
    @Data
    public static class MessageData {
        @JsonProperty("text")
        private String text;
        @JsonProperty("file_id")
        private String fileId;
        @JsonProperty("path")
        private String path;
        @JsonProperty("file")
        private String file;
        @JsonProperty("url")
        private String url;
        @JsonProperty("mention")
        private String mention;
        @JsonProperty("qq")
        private String qq;
        @JsonProperty("id")
        private String id;
    }
}
