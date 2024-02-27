package plus.easydo.dnf.onebot.enums;

import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-02-21 16:21
 * @Description 系统通知子类型
 */
public enum OneBotPostNoticeNotifySubTypeEnum {




    HONOR("honor", "群荣誉变更"),
    POKE("poke", "戳一戳"),
    LUCKY_KING("lucky_king", "群红包幸运王"),
    TITLE("title", "群成员头衔变更");

    private final String type;
    private final String desc;

    OneBotPostNoticeNotifySubTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByType(String mode) {
        for (OneBotPostNoticeNotifySubTypeEnum postRequestTypeEnum : values()) {
            if (Objects.equals(postRequestTypeEnum.type, mode)) {
                return postRequestTypeEnum.desc;
            }
        }
        return "";
    }
}
