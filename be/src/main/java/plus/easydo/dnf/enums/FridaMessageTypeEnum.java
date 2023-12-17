package plus.easydo.dnf.enums;


import lombok.Getter;
import plus.easydo.dnf.constant.SystemConstant;

/**
 * @author yuzhanfeng
 * @Date 2023-12-04 9:42
 * @Description frida消息类型
 */
@Getter
public enum FridaMessageTypeEnum {

    CLIENT("client","客户端信息"),
    CLIENT_AUTH("client_auth","客户端鉴权信息"),
    FLUSH_CONF("flush_conf","刷新配置信息"),
    FRIDA("frida","frida消息"),
    DEBUG("debug","debug消息"),
    SEND_MAIL("send_mail","发送邮件"),
    SEND_NOTICE("send_notice","发送公告"),
    USER_LOGIN(SystemConstant.GAME_USER_LOGIN,"用户登录"),
    USER_EXIT(SystemConstant.GAME_USER_EXIT,"用户退出");


    private final String code;
    private final String name;

    FridaMessageTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

}
