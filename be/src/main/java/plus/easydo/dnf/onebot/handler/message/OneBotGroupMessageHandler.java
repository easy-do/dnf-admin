package plus.easydo.dnf.onebot.handler.message;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.onebot.model.OneBotLoginInfo;
import plus.easydo.dnf.onebot.model.OneBotSender;


/**
 * @author yuzhanfeng
 * @Date 2024/2/25
 * @Description qq群消息处理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OneBotGroupMessageHandler {

    private OneBotLoginInfo loginInfo;

    private final OneBotGroupAtMessageHandler groupAtMessageHandler;

    public String handlerMessage(String botNumber, String groupId, OneBotSender sender, String message){


        return "";
    }

}
