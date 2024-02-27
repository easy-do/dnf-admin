package plus.easydo.dnf.config;

import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.keygen.KeyGeneratorFactory;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author laoyu
 * @version 1.0
 * @description MyBatisFlex配置
 * @date 2023/10/14
 */

@Configuration
public class MyBatisFlexConfiguration implements MyBatisFlexCustomizer {

    private static final Logger logger = LoggerFactory
            .getLogger("mybatis-flex-sql");


    public MyBatisFlexConfiguration() {
        //开启审计功能
//        AuditManager.setAuditEnable(true);

        //设置 SQL 审计收集器
//        AuditManager.setMessageCollector(auditMessage ->
//                logger.info("{},{}ms", auditMessage.getFullSql()
//                        , auditMessage.getElapsedTime())
//        );
        KeyGeneratorFactory.register("UUIDKeyGenerator", new UUIDKeyGenerator());
    }

    @Override
    public void customize(FlexGlobalConfig globalConfig) {

    }
}
