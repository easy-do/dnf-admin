package plus.easydo.dnf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import plus.easydo.dnf.onebot.websocket.OneBotWebSocketHandler;
import plus.easydo.dnf.websocket.FcWebSocketHandler;

/**
 * @author laoyu
 * @version 1.0
 * @description WebSocketConfig
 * @date 2023/12/2
 */

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //配置handler,拦截器和跨域
        registry.addHandler(fcWebSocketHandler(), "/fc/ws").setAllowedOrigins("*");
        registry.addHandler(oneBotWebSocketHandler(), "/ws/oneBot").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler fcWebSocketHandler() {
        return new FcWebSocketHandler();
    }

    @Bean
    public WebSocketHandler oneBotWebSocketHandler() {
        return new OneBotWebSocketHandler();
    }

//    @Bean
//    public ServletServerContainerFactoryBean createWebSocketContainer() {
//        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
//        container.setMaxTextMessageBufferSize(-1);  //文本消息最大缓存
//        container.setMaxBinaryMessageBufferSize(-1);  //二进制消息最大缓存
//        container.setMaxSessionIdleTimeout(3L * 60 * 1000); // 最大闲置时间，3分钟没动自动关闭连接
//        container.setAsyncSendTimeout(10L * 1000); //异步发送超时时间
//        return container;
//    }
}
