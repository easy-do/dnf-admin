package plus.easydo.dnf.enums;

import java.util.Objects;

/**
 * 稀有度枚举
 *
 * @author laoyu
 * @date 2023/10/15
 */
public enum RarityEnum {

    ORDINARY(0, "普通"),
    SENIOR(1, "高级"),
    RARE(2, "稀有"),
    ARTIFACT(3, "神器"),
    EPIC(4, "史诗"),
    BRAVE(5, "勇者"),
    LEGEND(6, "传说"),
    MYTH(7, "神话");

    private final Integer rarityCode;
    private final String rarityName;

    RarityEnum(Integer rarityCode, String rarityName) {
        this.rarityCode = rarityCode;
        this.rarityName = rarityName;
    }

    public static String getByCode(Integer jobCode) {
        for (RarityEnum rarityEnum : values()) {
            if (Objects.equals(rarityEnum.rarityCode, jobCode)) {
                return rarityEnum.rarityName;
            }
        }
        return "";
    }

}
