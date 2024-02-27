package plus.easydo.dnf.onebot.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import plus.easydo.dnf.entity.DaBotInfo;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.onebot.OneBotConstants;
import plus.easydo.dnf.onebot.enums.OneBotMessageTypeEnum;
import plus.easydo.dnf.onebot.model.OneBotLoginInfo;
import plus.easydo.dnf.onebot.model.OneBotMessage;
import plus.easydo.dnf.onebot.model.OneBotMessageParse;
import plus.easydo.dnf.onebot.websocket.OneBotWebSocketHandler;

import java.util.Objects;


/**
 * @author yuzhanfeng
 * @Date 2024/2/25
 * @Description bot工具
 */
public class OneBotUtils {


    private OneBotUtils() {
    }

    private static String getRequest(String botNumber, String path) {
        DaBotInfo bot = CacheManager.BOT_CACHE.get(botNumber);
        if (Objects.isNull(bot)) {
            throw new BaseException("没有找到机器人[" + botNumber + "]信息");
        }
        return HttpRequest.get(bot.getBotUrl() + "/" + path)
                .header(OneBotConstants.HEADER_AUTHORIZATION, OneBotConstants.HEADER_AUTHORIZATION_VALUE_PRE + bot.getBotSecret())
                .execute().body();
    }

    private static String postRequest(String botNumber, String path, JSONObject body) {
        DaBotInfo bot = CacheManager.BOT_CACHE.get(botNumber);
        if (Objects.isNull(bot)) {
            throw new BaseException("没有找到机器人[" + botNumber + "]信息");
        }
        return HttpRequest.post(bot.getBotUrl() + "/" + path)
                .header(OneBotConstants.HEADER_AUTHORIZATION, OneBotConstants.HEADER_AUTHORIZATION_VALUE_PRE + bot.getBotSecret())
                .body(body.toStringPretty())
                .execute().body();
    }

    private static String sendSocketAwait(String botNumber, String action, JSONObject params){
        DaBotInfo bot = CacheManager.BOT_CACHE.get(botNumber);
        if (Objects.isNull(bot)) {
            throw new BaseException("没有找到机器人[" + botNumber + "]信息");
        }
        JSONObject message = JSONUtil.createObj();
        String messageId = UUID.fastUUID().toString(true);
        message.set("echo", messageId);
        message.set("action", action);
        if(Objects.nonNull(params)){
            message.set("params", params);
        }
        return OneBotWebSocketHandler.sendMessageAwaitRes(botNumber,messageId,message.toStringPretty());
    }

    private static void sendSocket(String botNumber, String action, JSONObject params){
        DaBotInfo bot = CacheManager.BOT_CACHE.get(botNumber);
        if (Objects.isNull(bot)) {
            throw new BaseException("没有找到机器人[" + botNumber + "]信息");
        }
        JSONObject message = JSONUtil.createObj();
        String messageId = UUID.fastUUID().toString(true);
        message.set("echo", messageId);
        message.set("action", action);
        if(Objects.nonNull(params)){
            message.set("params", params);
        }
        OneBotWebSocketHandler.sendMessage(botNumber,message.toStringPretty());
    }

    /**
     * 获取登录信息
     *
     * @return java.lang.String
     * @author laoyu
     * @date 2024-02-21
     */
    public static OneBotLoginInfo getLoginInfo(String botNumber) {
        String res = sendSocketAwait(botNumber, OneBotConstants.GET_LOGIN_INFO,null);
        return JSONUtil.toBean(res, OneBotLoginInfo.class);
    }

    /**
     * 获取群列表
     *
     * @return java.lang.String
     * @author laoyu
     * @date 2024-02-21
     */
    public static String getGroupList(String botNumber) {
        return getRequest(botNumber, OneBotConstants.GET_GROUP_LIST);
    }

    /**
     * 发送群消息
     *
     * @param groupId    groupId
     * @param message    message
     * @param autoEscape 是否纯文本
     * @return java.lang.String
     * @author laoyu
     * @date 2024-02-21
     */
    public static void sendGroupMessage(String botNumber, String groupId, String message, boolean autoEscape) {
        JSONObject body = JSONUtil.createObj();
        body.set("group_id", groupId);
        body.set("message", message);
        body.set("auto_escape", autoEscape);
        sendSocket(botNumber, OneBotConstants.SEND_GROUP_MSG, body);
    }

    /**
     * 撤回消息
     *
     * @param messageId messageId
     * @return java.lang.String
     * @author laoyu
     * @date 2024-02-21
     */
    public static void deleteMsg(String botNumber, String messageId) {
        JSONObject body = JSONUtil.createObj();
        body.set("message_id", messageId);
        sendSocket(botNumber, OneBotConstants.DELETE_MSG, body);
    }

    /**
     * 设置单个禁言
     *
     * @param groupId  groupId
     * @param userId   userId
     * @param duration duration
     * @return java.lang.String
     * @author laoyu
     * @date 2024/2/21
     */
    public static void setGroupBan(String botNumber, String groupId, String userId, Long duration) {
        //group_id	int64	-	群号
        //user_id	int64	-	要禁言的 QQ 号
        //duration	uint32	30 * 60	禁言时长, 单位秒, 0 表示取消禁言
        JSONObject body = JSONUtil.createObj();
        body.set("group_id", groupId);
        body.set("user_id", userId);
        body.set("duration", duration * 60);
        sendSocket(botNumber, OneBotConstants.SET_GROUP_BAN, body);
    }

    /**
     * 设置全体禁言
     *
     * @param groupId groupId
     * @param enable  enable
     * @return java.lang.String
     * @author laoyu
     * @date 2024/2/21
     */
    public static void setGroupWholeBan(String botNumber, String groupId, boolean enable) {
        //group_id	int64	-	群号
        JSONObject body = JSONUtil.createObj();
        body.set("group_id", groupId);
        body.set("enable", enable);
        sendSocket(botNumber, OneBotConstants.SET_GROUP_WHOLE_BAN, body);
    }

    /**
     * 群组踢人
     *
     * @param groupId          群号
     * @param userId           要踢的 QQ 号
     * @param rejectAddRequest 拒绝此人的加群请求
     * @return java.lang.String
     * @author laoyu
     * @date 2024/2/21
     */
    public static void setGroupKick(String botNumber, String groupId, String userId, boolean rejectAddRequest) {
        JSONObject body = JSONUtil.createObj();
        body.set("group_id", groupId);
        body.set("user_id", userId);
        body.set("reject_add_request", rejectAddRequest);
        sendSocket(botNumber, OneBotConstants.SET_GROUP_KICK, body);
    }

    public static long getPostTime(JSONObject postData){
        Long time = postData.getLong(OneBotConstants.TIME);
        String timeStr = String.valueOf(time);
        if(timeStr.length() == 10){
            return time * 1000;
        }
        return time;
    }

    /**
     * 消息段解析
     *
     * @param message message
     * @return plus.easydo.dnf.onebot.model.OneBotMessageParse
     * @author laoyu
     * @date 2024/2/25
     */
    public static OneBotMessageParse parseMessage(String message){
        JSONArray messageArray = JSONUtil.parseArray(message);
        OneBotMessageParse oneBotMessageParse = new OneBotMessageParse();
        oneBotMessageParse.setSourceMessage(message);
        oneBotMessageParse.setSegmentSize(messageArray.size());
        //是个空数组的情况
        if(messageArray.isEmpty()){
            oneBotMessageParse.setType(OneBotMessageTypeEnum.EMPTY.getType());
            oneBotMessageParse.setSimpleMessage(message);
            return oneBotMessageParse;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < messageArray.size(); i++) {
            Object messageObj = messageArray.get(i);
            String sp = "|<-";
            OneBotMessage oneBotMessage = JSONUtil.toBean(messageObj.toString(), OneBotMessage.class);
            if(oneBotMessage.getType().equals(OneBotMessageTypeEnum.AT.getType())){
                stringBuilder.append(oneBotMessage.getType()).append("->|").append(oneBotMessage.getData().getMention());
            }
            else if(oneBotMessage.getType().equals(OneBotMessageTypeEnum.TEXT.getType())){
                stringBuilder.append(oneBotMessage.getType()).append("->|").append(oneBotMessage.getData().getText());
            }
            else if(oneBotMessage.getType().equals(OneBotMessageTypeEnum.FACE.getType())){
                stringBuilder.append(oneBotMessage.getType()).append("->|").append(oneBotMessage.getData().getId());
            }
            else if(oneBotMessage.getType().equals(OneBotMessageTypeEnum.IMAGE.getType())){
                stringBuilder.append(oneBotMessage.getType()).append("->|").append(oneBotMessage.getData().getUrl());
            }
            else {
                stringBuilder.append(oneBotMessage.getType()).append("->|").append(oneBotMessage.getData());
            }
            if(messageArray.size() > 1 && i != messageArray.size()-1){
                stringBuilder.append(sp);
            }
        }
        oneBotMessageParse.setParseMessage(stringBuilder.toString());
        //就一个消息段
        if(messageArray.size() == 1){
            OneBotMessage oneBotMessage = JSONUtil.toBean(messageArray.get(0).toString(), OneBotMessage.class);
            oneBotMessageParse.setSimpleMessage(oneBotMessage.getData().getText());
            oneBotMessageParse.setType(oneBotMessage.getType());
        }else
        //两个的支持解析
        if(messageArray.size() == 2){
            OneBotMessage oneBotMessage1 = JSONUtil.toBean(messageArray.get(0).toString(), OneBotMessage.class);
            OneBotMessage oneBotMessage2 = JSONUtil.toBean(messageArray.get(1).toString(), OneBotMessage.class);
            if(oneBotMessage1.getType().equals(OneBotMessageTypeEnum.AT.getType()) && oneBotMessage2.getType().equals(OneBotMessageTypeEnum.TEXT.getType())){
                oneBotMessageParse.setAtUser(oneBotMessage1.getData().getQq());
                oneBotMessageParse.setAtAfterText(oneBotMessage2.getData().getText());
                oneBotMessageParse.setType(OneBotMessageTypeEnum.AT_TEXT.getType());
            }else if(oneBotMessage1.getType().equals(OneBotMessageTypeEnum.TEXT.getType()) && oneBotMessage2.getType().equals(OneBotMessageTypeEnum.AT.getType())){
                oneBotMessageParse.setAtBeforeText(oneBotMessage1.getData().getText());
                oneBotMessageParse.setAtUser(oneBotMessage2.getData().getQq());
                oneBotMessageParse.setType(OneBotMessageTypeEnum.TEXT_AT.getType());
            }else if(oneBotMessage1.getType().equals(OneBotMessageTypeEnum.AT.getType()) && oneBotMessage2.getType().equals(OneBotMessageTypeEnum.FACE.getType())){
                oneBotMessageParse.setAtUser(oneBotMessage1.getData().getQq());
                oneBotMessageParse.setAtAfterText(oneBotMessage2.getData().getId());
                oneBotMessageParse.setType(OneBotMessageTypeEnum.AT_FACE.getType());
            }else if(oneBotMessage1.getType().equals(OneBotMessageTypeEnum.FACE.getType()) && oneBotMessage2.getType().equals(OneBotMessageTypeEnum.AT.getType())){
                oneBotMessageParse.setAtBeforeText(oneBotMessage1.getData().getId());
                oneBotMessageParse.setAtUser(oneBotMessage2.getData().getQq());
                oneBotMessageParse.setType(OneBotMessageTypeEnum.FACE_AT.getType());
            }else if(oneBotMessage1.getType().equals(OneBotMessageTypeEnum.TEXT.getType()) && oneBotMessage2.getType().equals(OneBotMessageTypeEnum.IMAGE.getType())){
                oneBotMessageParse.setType(OneBotMessageTypeEnum.TEXT_IMAGE.getType());
            }else if(oneBotMessage1.getType().equals(OneBotMessageTypeEnum.IMAGE.getType()) && oneBotMessage2.getType().equals(OneBotMessageTypeEnum.TEXT.getType())){
                oneBotMessageParse.setType(OneBotMessageTypeEnum.IMAGE_TEXT.getType());
            }else {
                oneBotMessageParse.setType(OneBotMessageTypeEnum.OTHER.getType());
            }
        }else
        //三个的也支持解析
        if(messageArray.size() == 3){
            OneBotMessage oneBotMessage1 = JSONUtil.toBean(messageArray.get(0).toString(), OneBotMessage.class);
            OneBotMessage oneBotMessage2 = JSONUtil.toBean(messageArray.get(1).toString(), OneBotMessage.class);
            OneBotMessage oneBotMessage3 = JSONUtil.toBean(messageArray.get(2).toString(), OneBotMessage.class);
            if(oneBotMessage1.getType().equals(OneBotMessageTypeEnum.TEXT.getType())
                    && oneBotMessage2.getType().equals(OneBotMessageTypeEnum.AT.getType())
                    &&oneBotMessage3.getType().equals(OneBotMessageTypeEnum.TEXT.getType())){
                oneBotMessageParse.setType(OneBotMessageTypeEnum.TEXT_AT_TEXT.getType());
                oneBotMessageParse.setAtUser(oneBotMessage2.getData().getQq());
                oneBotMessageParse.setAtBeforeText(oneBotMessage1.getData().getText());
                oneBotMessageParse.setAtAfterText(oneBotMessage3.getData().getText());
            }else {
                oneBotMessageParse.setType(OneBotMessageTypeEnum.OTHER.getType());
            }
        }else {
            //其他的
            oneBotMessageParse.setType(OneBotMessageTypeEnum.OTHER.getType());
        }
        return oneBotMessageParse;
    }
}
