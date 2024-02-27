package plus.easydo.dnf.onebot.websocket;


import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import plus.easydo.dnf.entity.DaBotInfo;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.onebot.OneBotConstants;
import plus.easydo.dnf.onebot.OneBotService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author laoyu
 * @version 1.0
 * @description FcWebSocketHandler
 * @date 2023/12/2
 */
@Slf4j
public class OneBotWebSocketHandler implements WebSocketHandler {

    private static final ConcurrentLinkedDeque<WebSocketSession> CONCURRENT_LINKED_DEQUE = new ConcurrentLinkedDeque<>();

    private static final Map<String, WebSocketSession> SESSION_MAP = new HashMap<>();
    private static final Map<String, String> SESSION_BOT_MAP = new HashMap<>();
    private static final Map<String, String> BOT_SESSION_MAP = new HashMap<>();
    private static final Cache<String,String> MESSAGE_CACHE = CacheUtil.newTimedCache(5000);

    private OneBotService oneBotService;

    private OneBotService getOneBotService(){
        if(Objects.isNull(oneBotService)){
            return SpringUtil.getBean(OneBotService.class);
        }
        return oneBotService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("机器人客户端连接:" + session.getId());
        //先鉴权
        HttpHeaders handshakeHeaders = session.getHandshakeHeaders();
        List<String> selfId = handshakeHeaders.get(OneBotConstants.HEADER_SELF_ID);
        if(Objects.nonNull(selfId) && !selfId.isEmpty()){
            DaBotInfo botInfo = CacheManager.BOT_CACHE.get(selfId.get(0));
            if(Objects.nonNull(botInfo)){
                String botSecret = botInfo.getBotSecret();
                if(CharSequenceUtil.isNotBlank(botSecret)){
                    List<String> authorization = handshakeHeaders.get(OneBotConstants.HEADER_AUTHORIZATION);
                    if(Objects.nonNull(authorization) && !authorization.isEmpty()){
                        if(CharSequenceUtil.equals(OneBotConstants.HEADER_AUTHORIZATION_VALUE_PRE + botSecret,authorization.get(0))){
                            log.info("机器人鉴权成功,保持会话连接.");
                            saveSession(session,botInfo);
                        }else {
                            log.warn("机器人鉴权失败,断开会话.");
                            closeSession(session);
                        }
                    }else {
                        log.warn("机器人未传递token,断开会话.");
                        closeSession(session);
                    }
                }else {
                    log.warn("机器人未设置密钥,跳过鉴权,保持会话连接.");
                    saveSession(session,botInfo);
                }
            }else {
                log.warn("与系统机器人匹配失败,断开会话.");
                closeSession(session);
            }
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        log.info("接收到客户端消息:" + message.getPayload());
        JSONObject messageJson = JSONUtil.parseObj(message.getPayload());
        if(Objects.nonNull(messageJson.getObj(OneBotConstants.POST_TYPE))){
            getOneBotService().handlerPost(messageJson);
        }
    }

    private void saveSession(WebSocketSession session, DaBotInfo botInfo){
        CONCURRENT_LINKED_DEQUE.add(session);
        SESSION_MAP.put(session.getId(),session);
        SESSION_BOT_MAP.put(session.getId(),botInfo.getBotNumber());
        SESSION_BOT_MAP.put(session.getId(),botInfo.getBotNumber());
        BOT_SESSION_MAP.put(botInfo.getBotNumber(),session.getId());
    }

    private void removeSession(WebSocketSession session){
        CONCURRENT_LINKED_DEQUE.remove(session);
        SESSION_MAP.remove(session.getId());
        String botNumber = SESSION_BOT_MAP.get(session.getId());
        SESSION_BOT_MAP.remove(session.getId());
        if(CharSequenceUtil.isNotBlank(botNumber)){
            BOT_SESSION_MAP.remove(botNumber);
        }
    }

    public static void sendMessage(String botNumber, String message){
        String sessionId = BOT_SESSION_MAP.get(botNumber);
        if(Objects.nonNull(sessionId)){
            WebSocketSession session = SESSION_MAP.get(sessionId);
            if(Objects.nonNull(session)){
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static String sendMessageAwaitRes(String botNumber, String messageId, String message){
        String sessionId = BOT_SESSION_MAP.get(botNumber);
        if(Objects.nonNull(sessionId)){
            WebSocketSession session = SESSION_MAP.get(sessionId);
            if(Objects.nonNull(session)){
                try {
                    session.sendMessage(new TextMessage(message));
                    String res = MESSAGE_CACHE.get(messageId);
                    int sleepTime = 0;
                    while(Objects.isNull(res) && sleepTime < 3000){
                        try {
                            Thread.sleep(500);
                            sleepTime = sleepTime+500;
                        } catch (InterruptedException e) {
                            throw new BaseException(ExceptionUtil.getMessage(e));
                        }
                        res = MESSAGE_CACHE.get(messageId);
                    }
                    return res;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return "";
    }

    private void closeSession(WebSocketSession session){
        try {
            session.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("机器人socket通信异常,:{},{}", session.getId(), ExceptionUtil.getMessage(exception));
        removeSession(session);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("机器人客户端断开连接:{},断开编码：{}" , session.getId(),closeStatus.getCode());
        removeSession(session);

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
