package plus.easydo.dnf.util;

import plus.easydo.dnf.enums.CallFunEnum;
import plus.easydo.dnf.vo.CallResult;

import java.util.Collections;

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
}
