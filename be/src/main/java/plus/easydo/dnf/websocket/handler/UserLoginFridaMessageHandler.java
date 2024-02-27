package plus.easydo.dnf.websocket.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.constant.SystemConstant;
import plus.easydo.dnf.service.SignInService;

/**
 * @author laoyu
 * @version 1.0
 * @description 角色登录处理
 * @date 2023/12/16
 */
@RequiredArgsConstructor
@Service(SystemConstant.GAME_USER_LOGIN)
public class UserLoginFridaMessageHandler implements FridaMessageHandler{

    private final SignInService signInService;

    @Override
    public void handler(String channel, Object data) {
        signInService.characSign(channel, Long.valueOf(data.toString()));
    }
}
