package plus.easydo.dnf.websocket.handler;


/**
 * @author laoyu
 * @version 1.0
 * @description frida消息处理
 * @date 2023/12/16
 */

public interface FridaMessageHandler {

    void handler(String channel, Object data);

}
