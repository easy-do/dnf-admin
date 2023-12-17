package plus.easydo.dnf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author laoyu
 * @version 1.0
 * @description 跨域设置
 * @date 2023/10/12
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 所有接口
                .allowCredentials(true) // 是否发送 Cookie
                .allowedOriginPatterns("*") // 支持域
                .allowedMethods("GET", "POST") // 支持方法
                .allowedHeaders("*")// 允许请求头
                .exposedHeaders("*");// 暴露出去的响应头
    }
}
