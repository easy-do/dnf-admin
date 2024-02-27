package plus.easydo.dnf.onebot.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.entity.DaBotEventScript;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.onebot.OneBotConstants;
import plus.easydo.dnf.onebot.utils.OneBotUtils;
import plus.easydo.dnf.util.AviatorScriptUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description OneBot上报消息js脚本处理处理
 * @date 2024/2/25
 */
@Slf4j
@Component
public class OneBotScriptPostHandler {
    public void handler(String postType, JSONObject postData){
        //通过事件找函数
        String botNumber = postData.getStr(OneBotConstants.SELF_ID);
        List<Long> scriptIds = CacheManager.EVENT_SCRIPT_ID_CACHE.get(postType);
        List<Long> botScriptIds = CacheManager.BOT_SCRIPT_ID_CACHE.get(botNumber);
        if(Objects.nonNull(scriptIds) && Objects.nonNull(botScriptIds)){
            List<Long> execFunIds = new ArrayList<>();
            //取到对应上的脚本
            scriptIds.forEach(scriptId->{
                if(botScriptIds.contains(scriptId)){
                    execFunIds.add(scriptId);
                }
            });
            execFunIds.forEach(functionId->{
                DaBotEventScript function = CacheManager.EVENT_SCRIPT_CACHE.get(functionId);
                if(Objects.nonNull(function) && CharSequenceUtil.isNotBlank(function.getScriptContent())){
                    log.info("执行脚本===============");
                    try {
                        Expression expression = AviatorScriptUtil.compile(function.getScriptContent());
                        Object message = postData.remove(OneBotConstants.MESSAGE);
                        if(Objects.nonNull(message)){
                            postData.set(OneBotConstants.MESSAGE, OneBotUtils.parseMessage(JSONUtil.toJsonStr(message)));
                        }
                        expression.execute(AviatorEvaluator.newEnv("postData",postData,"botNumber",botNumber));
                        log.info("脚本执行结束===============");
                    } catch (Exception e) {
                        log.warn("执行脚本异常：{}", ExceptionUtil.getMessage(e));
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
