package plus.easydo.dnf.handler;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.constant.ReportTypeConstant;
import plus.easydo.dnf.dto.UerLoginOutDto;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.service.SignInService;
import plus.easydo.dnf.vo.DpResult;

/**
 * @author laoyu
 * @version 1.0
 * @description 游戏登录事件处理
 * @date 2023/10/27
 */
@RequiredArgsConstructor
@Service(ReportTypeConstant.LOGIN)
public class LoginDpReportHandler implements DpReportHandler {

    private final SignInService signInService;
    @Override
    public DpResult handler(String type, String value) {
        UerLoginOutDto uerLoginOutDto = JSONUtil.toBean(value, UerLoginOutDto.class);
        CacheManager.GAME_ONLINE_USER.put(uerLoginOutDto.getUid(),uerLoginOutDto.getName());
        //判断角色是否领取签到奖励，如果未领取则发放奖励
        signInService.characSign(uerLoginOutDto.getCharacNo());
        return DpResult.build(ReportTypeConstant.LOGIN,true);
    }
}
