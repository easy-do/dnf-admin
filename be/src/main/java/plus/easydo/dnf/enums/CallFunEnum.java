package plus.easydo.dnf.enums;


import lombok.Getter;

/**
 * 执行函数枚举
 *
 * @author laoyu
 * @date 2023/10/28
 */
@Getter
public enum CallFunEnum {

    GAME_WORLD_SEND_NOTICE_PACKET_MESSAGE("game_world_send_notice_packet_message", "世界广播(频道内公告)"),
    SEND_MULTI_MAIL("send_multi_mail", "发系统邮件(多道具)(角色charac_no, 邮件标题, 邮件正文, 金币数量, 道具列表)"),
    FLUSHED_CONF("flushed_conf", "刷新游戏配置");

    private final String funName;
    private final String desc;

    CallFunEnum(String funName, String desc) {
        this.funName = funName;
        this.desc = desc;
    }

}
