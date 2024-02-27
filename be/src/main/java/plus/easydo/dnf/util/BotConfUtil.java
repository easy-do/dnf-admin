package plus.easydo.dnf.util;

import cn.hutool.extra.spring.SpringUtil;
import plus.easydo.dnf.entity.DaBotConf;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.service.BotService;

import java.util.Map;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-02-23 15:11
 * @Description 机器人配置工具类
 */
public class BotConfUtil {

    private static BotService botService;

    private BotConfUtil() {
    }

    public static BotService getBotService(){
        if (Objects.isNull(botService)) {
            botService = SpringUtil.getBean(BotService.class);
        }
        return botService;
    }

    public static Map<String, String> getAllBotConf(String botNumber){
        return CacheManager.BOT_CONF_CACHE.get(botNumber);
    }

    public static String getBotConf(String botNumber,String key){
        Map<String, String> conf = CacheManager.BOT_CONF_CACHE.get(botNumber);
        if(Objects.nonNull(conf)){
            String value = conf.get(key);
            return Objects.nonNull(value)?value:"";
        }
        return "";
    }

    public static boolean saveBotConf(String botNumber,String key, String value){
        DaBotConf botConf = DaBotConf.builder().botNumber(botNumber).confKey(key).confValue(value).build();
        return getBotService().addBotConf(botConf);
    }

    public static boolean updateBotConf(String botNumber,String key, String value){
        DaBotConf botConf = getBotService().getByBotNumberAndKey(botNumber,key);
        if(Objects.isNull(botConf)){
            return saveBotConf(botNumber,key,value);
        }
        botConf = DaBotConf.builder().id(botConf.getId()).confValue(value).build();
        return getBotService().updateBotConf(botConf);
    }

    public static boolean removeBotConf(String botNumber,String key){
        DaBotConf botConf = getBotService().getByBotNumberAndKey(botNumber,key);
        if(Objects.nonNull(botConf)){
            return getBotService().removeBotConf(botConf.getId());
        }
        return false;
    }
}
