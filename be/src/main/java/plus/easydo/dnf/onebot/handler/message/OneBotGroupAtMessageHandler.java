package plus.easydo.dnf.onebot.handler.message;

import org.springframework.stereotype.Component;
import plus.easydo.dnf.onebot.model.OneBotSender;


/**
 * @author yuzhanfeng
 * @Date 2024/2/25
 * @Description qq群艾特我的消息
 */
@Component
public class OneBotGroupAtMessageHandler {

    public String handlerMessage(String groupId, OneBotSender sender, String message){
//        OneBotUtils.sendGroupMessage(groupId, CharSequenceUtil.format(CqConstant.AT_MESSAGE_PRE,sender.getUserId())+message,false);
        return "";
    }
}
