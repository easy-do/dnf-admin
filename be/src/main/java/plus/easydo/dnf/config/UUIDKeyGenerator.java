package plus.easydo.dnf.config;

import cn.hutool.core.lang.UUID;
import com.mybatisflex.core.keygen.IKeyGenerator;

/**
 * @author yuzhanfeng
 * @Date 2024-01-02 17:33
 * @Description 自定义uuid生成
 */
public class UUIDKeyGenerator implements IKeyGenerator {

    @Override
    public Object generate(Object entity, String keyColumn) {
        return UUID.fastUUID().toString(true);
    }
}
