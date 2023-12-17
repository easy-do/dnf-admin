package plus.easydo.dnf.config;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.text.CharSequenceUtil;
import com.mybatisflex.annotation.UpdateListener;
import plus.easydo.dnf.entity.DaChannel;

/**
 * @author laoyu
 * @version 1.0
 * @description base64加密
 * @date 2023/12/7
 */

public class ChannelBase64UpdateListener implements UpdateListener {
    @Override
    public void onUpdate(Object entity) {
        DaChannel channel = (DaChannel)entity;
        String scriptContext = channel.getScriptContext();
        if(CharSequenceUtil.isNotBlank(scriptContext) && !Base64.isBase64(scriptContext)){
            channel.setScriptContext(Base64.encode(CharSequenceUtil.bytes(scriptContext)));
        }
        String mainPython = channel.getMainPython();
        if(CharSequenceUtil.isNotBlank(mainPython) && !Base64.isBase64(mainPython)){
            channel.setMainPython(Base64.encode(CharSequenceUtil.bytes(mainPython)));
        }
    }
}
