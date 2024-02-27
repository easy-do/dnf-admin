package plus.easydo.dnf.onebot.model;

import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description OntBot消息转换
 * @date 2024/2/25
 */
@Data
public class OneBotMessageParse {

    /**OneBot传过来的原文*/
    private String sourceMessage;

    /**消息段大小*/
    private Integer segmentSize;

    /**就一组转换后的消息*/
    private String simpleMessage;

    /**消息类型*/
    private String type;

    /**转换后的*/
    private String parseMessage;

    /**被艾特人*/
    private String atUser;
    /**艾特前面的文字*/
    private String atBeforeText;
    /**艾特后面的文字*/
    private String atAfterText;

}
