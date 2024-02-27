package plus.easydo.dnf.onebot.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-02-21 11:13
 * @Description cq上报消息类型枚举
 */
@Getter
public enum OneBotPostMessageTypeEnum {


    PRIVATE("private", "私聊消息"),
    GROUP("group", "群消息");

    private final String type;
    private final String desc;

    OneBotPostMessageTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByType(String mode) {
        for (OneBotPostMessageTypeEnum oneBotPostMessageTypeEnum : values()) {
            if (Objects.equals(oneBotPostMessageTypeEnum.type, mode)) {
                return oneBotPostMessageTypeEnum.desc;
            }
        }
        return "";
    }
}
