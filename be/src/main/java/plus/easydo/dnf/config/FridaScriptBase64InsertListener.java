package plus.easydo.dnf.config;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.text.CharSequenceUtil;
import com.mybatisflex.annotation.InsertListener;
import plus.easydo.dnf.entity.DaFridaScript;

/**
 * @author laoyu
 * @version 1.0
 * @description base64加密
 * @date 2023/12/7
 */

public class FridaScriptBase64InsertListener implements InsertListener {
    @Override
    public void onInsert(Object entity) {
        DaFridaScript fridaScript = (DaFridaScript)entity;
        String scriptContext = fridaScript.getScriptContext();
        if(CharSequenceUtil.isNotBlank(scriptContext) && !Base64.isBase64(scriptContext)){
            fridaScript.setScriptContext(Base64.encode(CharSequenceUtil.bytes(scriptContext)));
        }
    }
}
