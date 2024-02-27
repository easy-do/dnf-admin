package plus.easydo.dnf.enums;


import lombok.Getter;
import plus.easydo.dnf.constant.SystemConstant;

/**
 * @author yuzhanfeng
 * @Date 2023-12-04 9:42
 * @Description 角色物品类型
 */
@Getter
public enum RoleItemTypeEnum {

    ALL("all","所有"),
    EQ("eq","装备栏"),
    INVENTORY("inventory","背包"),
    CREATURE("creature","宠物栏"),
    CARGO("cargo","仓库"),
    ACCOUNT_CARGO("account_cargo","账号仓库");


    private final String type;
    private final String name;

    RoleItemTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

}
