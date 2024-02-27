package plus.easydo.dnf.onebot.handler;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.entity.DaBotMessage;
import plus.easydo.dnf.manager.DaBotMessageManager;
import plus.easydo.dnf.onebot.OneBotConstants;
import plus.easydo.dnf.onebot.enums.OneBotPostMessageTypeEnum;
import plus.easydo.dnf.onebot.handler.message.OneBotGroupMessageHandler;
import plus.easydo.dnf.onebot.handler.message.OneBotGroupPrivateMessageHandler;
import plus.easydo.dnf.onebot.handler.message.OneBotPrivateMessageHandler;
import plus.easydo.dnf.onebot.model.OneBotMessageParse;
import plus.easydo.dnf.onebot.model.OneBotSender;
import plus.easydo.dnf.onebot.utils.OneBotUtils;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author laoyu
 * @version 1.0
 * @description OneBot上报消息处理
 * @date 2024/2/25
 */
@Slf4j
@Service("message")
@RequiredArgsConstructor
public class OneBotPostMessageHandler implements OneBotPostHandler{

    private final OneBotGroupMessageHandler groupMessageHandler;

    private final OneBotPrivateMessageHandler privateMessageHandler;

    private final OneBotGroupPrivateMessageHandler groupPrivateMessageHandler;

    private final DaBotMessageManager daBotMessageManager;

    @Override
    public void handlerPost(JSONObject postData) {
        long time = OneBotUtils.getPostTime(postData);
        String messageType = postData.getStr(OneBotConstants.MESSAGE_TYPE);
        String message = postData.getStr(OneBotConstants.MESSAGE);
        String selfId = postData.getStr(OneBotConstants.SELF_ID);
        Object messageId = postData.get(OneBotConstants.MESSAGE_ID);
        log.debug("接收到消息,类型:{},内容:{}", OneBotPostMessageTypeEnum.getDescByType(messageType), message);
        Object groupId = postData.get(OneBotConstants.GROUP_ID);
        JSONObject senderJson = postData.getJSONObject(OneBotConstants.SENDER);
        OneBotSender sender = JSONUtil.toBean(senderJson, OneBotSender.class);
        OneBotMessageParse oneBotMessageParse = OneBotUtils.parseMessage(message);
        if(oneBotMessageParse.getSegmentSize() != 0){
            DaBotMessage daBotMessage = DaBotMessage.builder()
                    .sendUser(String.valueOf(sender.getUserId()))
                    .selfUser(selfId)
                    .selfTime(LocalDateTimeUtil.of(time))
                    .message(oneBotMessageParse.getSegmentSize() == 1 ? oneBotMessageParse.getSimpleMessage():oneBotMessageParse.getParseMessage())
                    .messageId(String.valueOf(messageId))
                    .build();
            if (CharSequenceUtil.equals(messageType,OneBotPostMessageTypeEnum.PRIVATE.getType())){
                //群内发起的私聊
                if(Objects.nonNull(groupId)){
                    daBotMessage.setGroupId(String.valueOf(groupId));
                    groupPrivateMessageHandler.handlerMessage(String.valueOf(groupId), sender, message);
                }else {
                    //直接私聊
                    privateMessageHandler.handlerMessage(sender, message);
                }
            }else {
                daBotMessage.setGroupId(String.valueOf(groupId));
                groupMessageHandler.handlerMessage(selfId, String.valueOf(groupId), sender, message);
            }
            CompletableFuture.runAsync(()->daBotMessageManager.save(daBotMessage));
        }
    }
}
