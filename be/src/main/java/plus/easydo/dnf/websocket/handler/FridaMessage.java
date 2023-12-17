package plus.easydo.dnf.websocket.handler;

import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description frida上报的消息
 * @date 2023/12/16
 */
@Data
public class FridaMessage {

    private String type;
    private String data;

}
