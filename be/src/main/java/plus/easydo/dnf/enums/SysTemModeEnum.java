package plus.easydo.dnf.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * 系统模式枚举
 *
 * @author laoyu
 * @date 2024-02-19
 */
@Getter
public enum SysTemModeEnum {

    DEFAULT("default", "默认模式"),
    STANDALONE("standalone", "独立部署模式"),
    UTILS("utils", "工具模式");

    private final String mode;
    private final String desc;

    SysTemModeEnum(String mode, String desc) {
        this.mode = mode;
        this.desc = desc;
    }

    public static String getDescByMode(String mode) {
        for (SysTemModeEnum rarityEnum : values()) {
            if (Objects.equals(rarityEnum.mode, mode)) {
                return rarityEnum.desc;
            }
        }
        return "";
    }

}
