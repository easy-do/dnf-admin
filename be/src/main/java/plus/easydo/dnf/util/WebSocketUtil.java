package plus.easydo.dnf.util;

import cn.hutool.json.JSONUtil;
import plus.easydo.dnf.dto.MailItemDto;
import plus.easydo.dnf.dto.SendMailDto;
import plus.easydo.dnf.enums.FridaMessageTypeEnum;
import plus.easydo.dnf.vo.FcResult;
import plus.easydo.dnf.websocket.FcWebSocketHandler;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description wesocket工具
 * @date 2023/12/16
 */

public class WebSocketUtil {

    private WebSocketUtil() {
    }


   public static void sendMail(SendMailDto sendMailDto){
       List<MailItemDto> itemList = sendMailDto.getItemList();
       if(Objects.isNull(itemList) || itemList.isEmpty()){
           sendMailDto.setItemList(Collections.singletonList(MailItemDto.builder().itemId(0L).count(0L).build()));
       }
       FcResult r = FcResult.builder().type(FridaMessageTypeEnum.SEND_MAIL.getCode()).data(sendMailDto).build();
       FcWebSocketHandler.sendMessageFirst(JSONUtil.toJsonStr(r));
   }


    public static void sendMail(String channel, SendMailDto sendMailDto){
        FcResult r = FcResult.builder().type(FridaMessageTypeEnum.SEND_MAIL.getCode()).data(sendMailDto).build();
        FcWebSocketHandler.sendMessage(channel,JSONUtil.toJsonStr(r));
    }

    public static void sendNotice(String message) {
        FcResult r = FcResult.builder().type(FridaMessageTypeEnum.SEND_NOTICE.getCode()).data(message).build();
        FcWebSocketHandler.sendMessage(JSONUtil.toJsonStr(r));
    }
    public static void saveChannelSecret(String channelName, String secret) {
        FcWebSocketHandler.saveChannelSecret(channelName, secret);
    }
    public static void flushConf() {
        FcResult r = FcResult.builder().type(FridaMessageTypeEnum.FLUSH_CONF.getCode()).data(FridaMessageTypeEnum.FLUSH_CONF.getName()).build();
        FcWebSocketHandler.sendMessage(JSONUtil.toJsonStr(r));
    }

}
