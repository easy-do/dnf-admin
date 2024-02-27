package plus.easydo.dnf.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * 系统模式枚举
 *
 * @author laoyu
 * @date 2024-02-21
 */
@Getter
public enum BotPlatromEnum {

    QQ("qq", "腾讯QQ"),
    OTHER("standalone", "未知");

    private final String type;
    private final String desc;

    BotPlatromEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByType(String type) {
        for (BotPlatromEnum rarityEnum : values()) {
            if (Objects.equals(rarityEnum.type, type)) {
                return rarityEnum.desc;
            }
        }
        return "";
    }

}
