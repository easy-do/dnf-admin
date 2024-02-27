package plus.easydo.dnf.util;

import cn.hutool.extra.spring.SpringUtil;
import plus.easydo.dnf.dto.SendMailDto;
import plus.easydo.dnf.service.GameMailService;

import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-02-26 19:07
 * @Description 机器人邮件工具类
 */
public class BotGameMailUtil {

    private BotGameMailUtil() {

    }
    private static GameMailService gameMailService;


    public static GameMailService getGameMailService(){
        if(Objects.isNull(gameMailService)){
            return SpringUtil.getBean(GameMailService.class);
        }
        return gameMailService;
    }

    public static void sendMail(SendMailDto sendMailDto){
        getGameMailService().sendMail(sendMailDto);
    }

}
