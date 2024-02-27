package plus.easydo.dnf.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * 日志类型枚举
 *
 * @author laoyu
 * @date 2024-02-04
 */
@Getter
public enum HistoryLogTypeEnum {
    ITEM_ADD("Item+","获得[{}]{}个"),
    ITEM_RE("Item-","失去[{}]{}个"),
    CARGO_ADD("Inven+","存仓库[{}]{}个"),
    CARGO_RE("Inven-","取仓库[{}]{}个"),
    ACCOUNT_CARGO_ADD("AccountCargo+","存金库[{}]{}个"),
    ACCOUNT_CARGO_RE("AccountCargo-","取金库[{}]{}个"),
    LEVEL_UP("Level+","{}升{}"),
    AVATAR_ADD("Avatar+","获得时装[{}]"),
    MONEY_ADD("Money+","金币增加[{}]"),
    MONEY_RE("Money-","金币减少[{}]"),
    LOGIN("IP+","登录游戏"),
    LOGOUT("IP-","退出游戏"),
    MAIL_R("MailR","读邮件[{}]"),
    KILL_MOB("KillMob","击杀怪物[{}]"),
    DUNGEON_LEAVE("DungeonLeave","离开副本[{}]"),
    DUNGEON_ENTER("DungeonEnter","进入副本[{}]"),
    MOVE_AREA("MoveArea","从{}移动至{}"),
    BUY_CASH_ITEM("BuyCashItem","商城购买-{}"),
    USE_SELECT_BOOSTER("UseSelectBooster","使用礼包[{}]"),
    UPGRADE_SUCCESS("Upgrade+","强化[{}],{}上{}"),
    UPGRADE_FAILED("Upgrade-","强化[{}],{}掉[{}]"),
    APPLY_ITEM("ApplyItem","对[{}]使用[{}]"),
    RANDOM_OPTION_ITEM("RandomOptionItem","重置[{}]属性"),
    QUEST_COMPLETE("QuestComplete","完成任务[{}]");

    HistoryLogTypeEnum(String type, String optionInfo) {
        this.type = type;
        this.optionInfo = optionInfo;
    }

    private final String type;

    private final String optionInfo;

    public static List<String> allType(){
        return Arrays.stream(HistoryLogTypeEnum.values()).map(HistoryLogTypeEnum::getType).toList();
    }
    public static List<String> oneParamType(){
        return Arrays.asList(UPGRADE_SUCCESS.type,UPGRADE_FAILED.type,RANDOM_OPTION_ITEM.type,CARGO_ADD.type,CARGO_RE.type);
    }
    public static List<String> twoParamType(){
        return Arrays.asList(ITEM_ADD.type,ITEM_RE.type,BUY_CASH_ITEM.type,ACCOUNT_CARGO_ADD.type,ACCOUNT_CARGO_RE.type);
    }

}
