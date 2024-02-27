package plus.easydo.dnf.onebot.enums;

import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-02-21 16:21
 * @Description 通知类型
 */
public enum OneBotPostNoticeTypeEnum {

    GROUP_UPLOAD("group_upload", "群文件上传"),
    GROUP_ADMIN("group_admin", "群管理员变更"),
    GROUP_DECREASE("group_decrease", "群成员减少"),
    GROUP_INCREASE("group_increase", "群成员增加"),
    GROUP_BAN("group_ban", "群成员禁言"),
    FRIEND_ADD("friend_add", "好友添加"),
    GROUP_RECALL("group_recall", "群消息撤回"),
    FRIEND_RECALL("friend_recall", "好友消息撤回"),
    GROUP_CARD("group_card", "群名片变更"),
    OFFLINE_FILE("offline_file", "离线文件上传"),
    CLIENT_STATUS("client_status", "客户端状态变更"),
    ESSENCE("essence", "精华消息"),
    NOTIFY("notify", "系统通知");

    private final String type;
    private final String desc;

    OneBotPostNoticeTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getDescByType(String mode) {
        for (OneBotPostNoticeTypeEnum postRequestTypeEnum : values()) {
            if (Objects.equals(postRequestTypeEnum.type, mode)) {
                return postRequestTypeEnum.desc;
            }
        }
        return "";
    }
}
