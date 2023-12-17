package plus.easydo.dnf.config;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.text.CharSequenceUtil;
import com.mybatisflex.annotation.InsertListener;
import plus.easydo.dnf.entity.DaFridaFunction;

/**
 * @author laoyu
 * @version 1.0
 * @description base64加密
 * @date 2023/12/7
 */

public class FridaFunctionBase64InsertListener implements InsertListener {
    @Override
    public void onInsert(Object entity) {
        DaFridaFunction fridaFunction = (DaFridaFunction)entity;
        String functionContext = fridaFunction.getFunctionContext();
        if(CharSequenceUtil.isNotBlank(functionContext) && !Base64.isBase64(functionContext)){
            fridaFunction.setFunctionContext(Base64.encode(CharSequenceUtil.bytes(functionContext)));
        }
    }
}
