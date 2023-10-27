package plus.easydo.dnf.handler;

import org.springframework.stereotype.Service;
import plus.easydo.dnf.constant.ReportTypeConstant;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.vo.DpResult;

/**
 * @author laoyu
 * @version 1.0
 * @description 游戏登录事件处理
 * @date 2023/10/27
 */
@Service(ReportTypeConstant.LOGIN)
public class LoginDpReportHandler implements DpReportHandler {
    @Override
    public DpResult handler(String type, String value) {
        CacheManager.GAME_ONLINE_USER.put(Integer.valueOf(value),true);
        return DpResult.build(ReportTypeConstant.LOGIN,true);
    }
}
