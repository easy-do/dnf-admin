package plus.easydo.dnf.websocket.handler;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.vo.FcResult;

import java.util.Map;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description frida消息处理
 * @date 2023/12/16
 */
@Slf4j
@Component
public class FridaMessageHandlerEndpoint {

    @Autowired
    private Map<String,FridaMessageHandler> fridaMessageHandlerMap;

    public void handler(FcResult fcResult){
        Object data = fcResult.getData();
        FridaMessage message = JSONUtil.toBean(JSONUtil.parseObj(data), FridaMessage.class);
        FridaMessageHandler handler = fridaMessageHandlerMap.get(message.getType());
        if(Objects.nonNull(handler)){
            handler.handler(fcResult.getChannel(),message.getData());
        }else {
            log.warn("没有找到frida消息处理程序：{}",fcResult.getType());
        }

    }

}
