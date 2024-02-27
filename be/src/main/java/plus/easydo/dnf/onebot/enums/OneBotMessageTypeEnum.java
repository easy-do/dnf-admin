package plus.easydo.dnf.onebot.enums;


import lombok.Getter;

import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description OneBot机器人消息类型
 * @date 2024/2/25
 */
@Getter
public enum OneBotMessageTypeEnum {

    EMPTY("empty", "空消息"),
    TEXT("text", "纯文本"),
    FACE("face", "表情"),
    IMAGE("image", "图片"),
    TEXT_IMAGE("text_image", "文字图片"),
    IMAGE_TEXT("image_text", "图片文字"),
    AT("at", "只艾特"),
    AT_TEXT("at_text", "艾特加文字"),
    TEXT_AT_TEXT("text_at_text", "文字加艾特加文字"),
    AT_FACE("at_face", "艾特加表情"),
    TEXT_AT("text_at", "文字加艾特"),
    FACE_AT("face_at", "表情加艾特"),
    TEXT_AT_OTHER("text_at_other", "文字加艾特加其他"),
    OTHER("other", "其他");

    private final String type;
    private final String desc;

    OneBotMessageTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByType(String mode) {
        for (OneBotMessageTypeEnum oneBotPostTypeEnum : values()) {
            if (Objects.equals(oneBotPostTypeEnum.type, mode)) {
                return oneBotPostTypeEnum.desc;
            }
        }
        return "";
    }
}
