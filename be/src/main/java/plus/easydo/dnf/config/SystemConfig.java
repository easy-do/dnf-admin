package plus.easydo.dnf.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
    private String adminRole = "admin";
    private String currentVersion;
    private String mode;
    private List<String> standaloneDisableResource;
    private List<String> utilsDisableResource;
}
