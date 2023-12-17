package plus.easydo.dnf.config;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.text.CharSequenceUtil;
import com.mybatisflex.annotation.SetListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description base64 解密
 * @date 2023/12/7
 */

public class Base64OnSetListener implements SetListener {
    private static final List<String> BASE64_FILED = new ArrayList<>();

    static {
        BASE64_FILED.add("scriptContext");
        BASE64_FILED.add("mainPython");
        BASE64_FILED.add("functionContext");
        BASE64_FILED.add("scriptContext");
    }

    @Override
    public Object onSet(Object entity, String property, Object value) {
        if(value != null && CharSequenceUtil.isNotBlank(value.toString()) && BASE64_FILED.contains(property) && Base64.isBase64(value.toString())){
            value = Base64.decodeStr(value.toString());
        }
        return value;
    }
}
