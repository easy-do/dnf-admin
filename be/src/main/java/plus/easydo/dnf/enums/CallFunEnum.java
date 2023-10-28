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

    GAME_WORLD_SEND_NOTICE_PACKET_MESSAGE("game_world_send_notice_packet_message", "世界广播(频道内公告)");

    private final String funName;
    private final String desc;

    CallFunEnum(String funName, String desc) {
        this.funName = funName;
        this.desc = desc;
    }

}
