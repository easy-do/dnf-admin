package plus.easydo.dnf.util;

import plus.easydo.dnf.enums.CallFunEnum;
import plus.easydo.dnf.vo.CallResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 执行命令构建工具类
 * @date 2023/10/29
 */

public class ExecCallBuildUtil {

    private ExecCallBuildUtil() {
    }

    public static CallResult buildNotice(String message){
        return CallResult.builder().callDp(false).callFrida(true).debug(true).funName(CallFunEnum.GAME_WORLD_SEND_NOTICE_PACKET_MESSAGE.getFunName()).args(Collections.singletonList(message)).build();
    }

    public static CallResult buildSendMultiMail(Integer characNo, String title, String text, Long gold, List<Object> itemList){
        List<Object> args = new ArrayList<>();
        args.add(characNo);
        args.add(title);
        args.add(text);
        args.add(gold);
        args.add(itemList);
        return CallResult.builder().callDp(false).callFrida(true).debug(true).funName(CallFunEnum.SEND_MULTI_MAIL.getFunName()).args(args).build();
    }
}
