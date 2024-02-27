package plus.easydo.dnf.onebot.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-02-21 16:21
 * @Description 请求类型
 */
@Getter
public enum OneBotPostRequestTypeEnum {
    FRIEND("friend", "好友请求"),
    GROUP("group", "群请求");

    private final String type;
    private final String desc;

    OneBotPostRequestTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByType(String mode) {
        for (OneBotPostRequestTypeEnum oneBotPostRequestTypeEnum : values()) {
            if (Objects.equals(oneBotPostRequestTypeEnum.type, mode)) {
                return oneBotPostRequestTypeEnum.desc;
            }
        }
        return "";
    }
}
