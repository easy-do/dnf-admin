package plus.easydo.dnf.websocket;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import plus.easydo.dnf.enums.FridaMessageTypeEnum;
import plus.easydo.dnf.vo.FcResult;
import plus.easydo.dnf.websocket.handler.FridaMessageHandlerEndpoint;

import java.util.ArrayList;
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
public class FcWebSocketHandler implements WebSocketHandler {

    private static final ConcurrentLinkedDeque<WebSocketSession> CONCURRENT_LINKED_DEQUE = new ConcurrentLinkedDeque<>();

    private static final Map<String, String> CHANNEL_SECRET_MAP = new HashMap<>();
    private static final Map<String, WebSocketSession> CHANNEL_SESSION_MAP = new HashMap<>();
    private static final Map<String, String> SESSION_CHANNELMAP = new HashMap<>();
    private static final Map<String, Cache<String, String>> CHANNEL_LOG_CACHE = new HashMap<>();

    public static List<String> getLog(String channel) {
        Cache<String, String> cache = CHANNEL_LOG_CACHE.get(channel);
        if (Objects.nonNull(cache)) {
            List<String> result = new ArrayList<>();
            cache.cacheObjIterator().forEachRemaining((ca) -> result.add(ca.getKey() + " " + ca.getValue()));
            return result;
        }
        return ListUtil.empty();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        CONCURRENT_LINKED_DEQUE.add(session);
        log.info("客户端连接:" + session.getId());
        sendMessage(session,JSONUtil.toJsonStr(FcResult.builder().type(FridaMessageTypeEnum.CLIENT.getCode()).data("connect success").build()));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        log.info("接收到客户端消息:" + message.getPayload());
        try {
            String messageStr = (String) message.getPayload();
            FcResult fcResult = JSONUtil.toBean(messageStr, FcResult.class);
            if (FridaMessageTypeEnum.CLIENT_AUTH.getCode().equals(fcResult.getType())) {
                String secret = fcResult.getSecret();
                String channel = fcResult.getChannel();
                String cacheSecret = CHANNEL_SECRET_MAP.get(channel);
                if(CharSequenceUtil.isNotBlank(cacheSecret) && CharSequenceUtil.isNotBlank(channel) && CharSequenceUtil.equals(secret,cacheSecret)){
                    CHANNEL_SESSION_MAP.put(channel, session);
                    SESSION_CHANNELMAP.put(session.getId(), channel);
                    CHANNEL_LOG_CACHE.put(channel, CacheUtil.newFIFOCache(1000));
                }else {
                    log.info("客户端鉴权失败,断开连接");
                    session.close();
                }
            }else {
                String cache = SESSION_CHANNELMAP.get(session.getId());
                if(CharSequenceUtil.isBlank(cache)){
                    log.info("检测到未经鉴权的客户端发送消息,断开客户端连接");
                    session.close();
                }
                FridaMessageHandlerEndpoint fridaMessageHandlerEndpoint = SpringUtil.getBean(FridaMessageHandlerEndpoint.class);
                if(Objects.nonNull(fridaMessageHandlerEndpoint)){
                    fridaMessageHandlerEndpoint.handler(fcResult);
                }
            }
        } catch (Exception e) {
            log.warn("消息转换失败:{}", ExceptionUtil.getMessage(e));
        }finally {
            saveLog(session, "<==="+ message.getPayload());
        }

    }

    public static void saveLog(WebSocketSession session, String log) {
        String channel = SESSION_CHANNELMAP.get(session.getId());
        if (Objects.nonNull(channel)) {
            Cache<String, String> cache = CHANNEL_LOG_CACHE.get(channel);
            cache.put(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), DatePattern.NORM_DATETIME_MS_PATTERN), log);
        }
    }

    public static long getSessionCount(){
        return CONCURRENT_LINKED_DEQUE.size();
    }

    /**
     * 缓存频道通信密钥
     *
     * @param channel channel
     * @param secret secret
     * @author laoyu
     * @date 2023/12/16
     */
    public static void saveChannelSecret(String channel, String secret) {
        CHANNEL_SECRET_MAP.put(channel, secret);
    }

    /**
     * 向第一个频道的客户端发送消息
     *
     * @param message message
     * @author laoyu
     * @date 2023-12-04
     */
    public static void sendMessageFirst(String message) {
        if(!CONCURRENT_LINKED_DEQUE.isEmpty()){
            try {
                sendMessage(CONCURRENT_LINKED_DEQUE.getFirst(), message);
            } catch (Exception e) {
                log.warn("群发消息失败:{},{}", message, ExceptionUtil.getMessage(e));
            }
        }
    }

    /**
     * 向所有客户端发送消息
     *
     * @param message message
     * @author laoyu
     * @date 2023-12-04
     */
    public static void sendMessage(String message) {
        CONCURRENT_LINKED_DEQUE.forEach(item -> {
            try {
                 sendMessage(item, message);
            } catch (Exception e) {
                log.warn("群发消息失败:{},{}", message, ExceptionUtil.getMessage(e));
            }
        });
    }

    public static void sendMessage(WebSocketSession session, String message){
        try {
            if (Objects.nonNull(session) && session.isOpen()) {
                session.sendMessage(new TextMessage(message));
                saveLog(session,"===>"+message);
            } else {
                log.warn("向客户端发送消息失败,客户端已离线,{}", message);
                saveLog(session,"===>向客户端发送消息失败,客户端已离线,"+message);
            }
        } catch (Exception e) {
            log.warn("向客户端发送消息失败:{},{}", message, ExceptionUtil.getMessage(e));
        }
    }
    /**
     * 向指定频道发送消息
     *
     * @param channel channel
     * @param message message
     * @author laoyu
     * @date 2023-12-04
     */
    public static void sendMessage(String channel, String message) {
        WebSocketSession session = CHANNEL_SESSION_MAP.get(channel);
        sendMessage(session,message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("socket通信异常,:{},{}", session.getId(), ExceptionUtil.getMessage(exception));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("客户端断开连接:" + session.getId());
        CONCURRENT_LINKED_DEQUE.remove(session);
        String channel = SESSION_CHANNELMAP.get(session.getId());
        if (Objects.nonNull(channel)) {
            CHANNEL_SESSION_MAP.remove(channel);
            SESSION_CHANNELMAP.remove(session.getId());
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
