package plus.easydo.dnf.enums;

import java.util.Objects;

/**
 * 物品类型
 *
 * @author laoyu
 * @date 2023/10/15
 */
public enum ItemTypeEnum {

    EQUIPMENT(1,"装备"),
    CONSUMABLES(2,"消耗品"),
    MATERIAL(3,"材料"),
    MISSION_MATERIALS(4,"任务材料"),
    PET(5,"宠物"),
    PET_EQUIPMENT(6,"宠物装备"),
    PET_CONSUMABLES(7,"宠物消耗品"),
    FASHION(8,"时装"),
    EXPERT_JOB(10,"副职业");


    private final Integer code;
    private final String name;

    ItemTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer jobCode) {
        for (ItemTypeEnum rarityEnum : values()) {
            if (Objects.equals(rarityEnum.code, jobCode)) {
                return rarityEnum.name;
            }
        }
        return "";
    }

    public static Integer getCodeByName(String name) {
        for (ItemTypeEnum rarityEnum : values()) {
            if (Objects.equals(rarityEnum.name, name)) {
                return rarityEnum.code;
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
