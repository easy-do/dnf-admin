package plus.easydo.dnf.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.mybatisflex.spring.boot.MybatisFlexProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author laoyu
 * @version 1.0
 * @description flex的数据源工具类
 * @date 2023/10/16
 */
@Component
@RequiredArgsConstructor
public class FlexDataSourceUtil {

    private final MybatisFlexProperties mybatisFlexProperties;

    private static final Map<String,DruidDataSource> CACHE_MAP = new ConcurrentHashMap<>();

    public DruidDataSource getDataSource(String key){
        DruidDataSource cacheDs = CACHE_MAP.get(key);
        if(Objects.nonNull(cacheDs)){
            return cacheDs;
        }
        Map<String, Map<String, String>> datasourceMap = mybatisFlexProperties.getDatasource();
        Map<String, String> properties = datasourceMap.get(key);
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(properties.get("url"));
        dataSource.setUsername(properties.get("username"));
        dataSource.setPassword(properties.get("password"));
        dataSource.setConnectionInitSqls(Collections.singleton(properties.get("connectionInitSqls")));
        CACHE_MAP.put(key,dataSource);
        return dataSource;
    }

}
