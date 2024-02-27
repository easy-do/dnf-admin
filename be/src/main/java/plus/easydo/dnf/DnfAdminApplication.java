package plus.easydo.dnf;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.RuntimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author yuzhanfeng
 */
@Slf4j
@EnableScheduling
@MapperScan("plus.easydo.dnf.mapper")
@SpringBootApplication
public class DnfAdminApplication {


    public static void main(String[] args) {
        SpringApplication.run(DnfAdminApplication.class, args);
        String publicIp = System.getProperty("PUBLIC_IP");
        if(CharSequenceUtil.isBlank(publicIp)){
            try {
                publicIp = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.warn("当前服务地址获取失败");
            }
        }
        log.info(
                "服务启动成功," +
                        "\n\t本地访问地址: \t\t{}" +
                        "\n\t外部访问地址: \t\t{}\n"
                , "http://localhost:8888"
                , "http://"+publicIp+":8888"
        );
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            //如果是Windows系统
            RuntimeUtil.exec("cmd /c start " + "http://localhost:8888");
        }
    }

}
