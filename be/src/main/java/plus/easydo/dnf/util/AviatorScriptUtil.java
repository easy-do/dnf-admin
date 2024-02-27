package plus.easydo.dnf.util;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.Expression;
import plus.easydo.dnf.onebot.utils.OneBotUtils;

/**
 * @author laoyu
 * @version 1.0
 * @description AviatorScript工具类
 * @date 2024/2/22
 */

public class AviatorScriptUtil {

    private AviatorScriptUtil() {
    }

    private static final AviatorEvaluatorInstance INSTANCE = AviatorEvaluator.getInstance();

    static {

        try {
            AviatorEvaluator.importFunctions(CharSequenceUtil.class);
            AviatorEvaluator.importFunctions(BotConfUtil.class);
            AviatorEvaluator.importFunctions(OneBotUtils.class);
            AviatorEvaluator.importFunctions(String.class);
            AviatorEvaluator.importFunctions(BotScriptUtils.class);
            AviatorEvaluator.importFunctions(StrUtil.class);
            AviatorEvaluator.importFunctions(Long.class);
            AviatorEvaluator.importFunctions(Integer.class);
            AviatorEvaluator.importFunctions(BotGameSignInUtil.class);
            AviatorEvaluator.importFunctions(RandomUtil.class);
            AviatorEvaluator.importFunctions(BotAccountUtils.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Expression compile(String script){
        return AviatorEvaluator.getInstance().compile(script,true);
    }

    public static void main(String[] args) {
//        StrUtil.startWith()
//        StrUtil.trim();
//        StrUtil.contains()
//        int a = RandomUtil.randomInt(1, 3);
//        CharSequenceUtil.split()
    }

}
