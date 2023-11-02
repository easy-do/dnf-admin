package plus.easydo.dnf;

import cn.hutool.core.io.FileUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yuzhanfeng
 */
@MapperScan("plus.easydo.dnf.mapper")
@SpringBootApplication
public class DnfAdminApplication {

    public static void main(String[] args) {
        //将dp2文件copy到/data/dp2目录
        FileUtil.copy("/home/dp2","/data",false);
        SpringApplication.run(DnfAdminApplication.class, args);
    }

}
