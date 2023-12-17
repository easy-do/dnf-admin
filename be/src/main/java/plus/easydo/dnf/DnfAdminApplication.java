package plus.easydo.dnf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yuzhanfeng
 */
@EnableScheduling
@MapperScan("plus.easydo.dnf.mapper")
@SpringBootApplication
public class DnfAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(DnfAdminApplication.class, args);
    }

}
