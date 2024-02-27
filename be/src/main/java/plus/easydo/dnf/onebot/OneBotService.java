package plus.easydo.dnf.onebot;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.onebot.handler.OneBotPostHandler;
import plus.easydo.dnf.onebot.handler.OneBotScriptPostHandler;

import java.util.Map;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description OneBot服务
 * @date 2024/2/25
 */
@Component
@RequiredArgsConstructor
public class OneBotService {

    @Autowired
    private Map<String, OneBotPostHandler> postHandlerMap;

    private final OneBotScriptPostHandler oneBotScriptPostHandler;

    public void handlerPost(JSONObject postData){
        String postType = postData.getStr(OneBotConstants.POST_TYPE);
        if(CharSequenceUtil.isNotBlank(postType)){
            OneBotPostHandler postDataHandler = postHandlerMap.get(postType);
            if(Objects.nonNull(postDataHandler)){
                postDataHandler.handlerPost(postData);
            }
        }
        oneBotScriptPostHandler.handler(postType,postData);
    }
}
