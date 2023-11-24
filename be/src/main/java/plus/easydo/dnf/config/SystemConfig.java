package plus.easydo.dnf.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuzhanfeng
 * @Date 2023-11-24 10:50
 * @Description 系统配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "da")
public class SystemConfig {

    private String adminUser;
    private String dpGmKey;
    private String adminRole = "admin";
}
