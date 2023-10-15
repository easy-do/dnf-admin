package plus.easydo.dnf.enums;

import java.util.Objects;

/**
 * 增幅类型
 *
 * @author laoyu
 * @date 2023/10/15
 */
public enum AmplifyEnum {

    EMPTY(0, "空"),
    PHYSICAL(1, "异次元体力"),
    SPIRIT(2, "异次元精神"),
    POWER(3, "异次元力量"),
    INTELLIGENCE(4, "异次元智力");

    private final Integer code;
    private final String name;

    AmplifyEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer jobCode) {
        for (AmplifyEnum rarityEnum : values()) {
            if (Objects.equals(rarityEnum.code, jobCode)) {
                return rarityEnum.name;
            }
        }
        return "";
    }

    public static Integer getCodeByName(String name) {
        for (AmplifyEnum amplifyEnum : values()) {
            if (Objects.equals(amplifyEnum.name, name)) {
                return amplifyEnum.code;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
