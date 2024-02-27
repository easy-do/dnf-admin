package plus.easydo.dnf.onebot.enums;


import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description OneBot上报类型
 * @date 2024/2/25
 */

public enum OneBotPostTypeEnum {

    MESSAGE("message", "消息, 例如, 群聊消息"),
    MESSAGE_SENT("request", "请求事件"),
    NOTICE("notice", "通知事件"),
    META_EVENT("meta_event", "元事件");

    private final String type;
    private final String desc;

    OneBotPostTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByType(String mode) {
        for (OneBotPostTypeEnum oneBotPostTypeEnum : values()) {
            if (Objects.equals(oneBotPostTypeEnum.type, mode)) {
                return oneBotPostTypeEnum.desc;
            }
        }
        return "";
    }
}
