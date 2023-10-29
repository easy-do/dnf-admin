package plus.easydo.dnf.handler;

import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.constant.ReportTypeConstant;
import plus.easydo.dnf.dto.UerLoginOutDto;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.vo.DpResult;

/**
 * @author laoyu
 * @version 1.0
 * @description 游戏退出登录事件处理
 * @date 2023/10/27
 */
@Service(ReportTypeConstant.LOGOUT)
public class LogoutDpReportHandler implements DpReportHandler {
    @Override
    public DpResult handler(String type, String value) {
        UerLoginOutDto uerLoginOutDto = JSONUtil.toBean(value, UerLoginOutDto.class);
        CacheManager.GAME_ONLINE_USER.remove(uerLoginOutDto.getUid());
        return DpResult.build(ReportTypeConstant.LOGOUT,true);
    }
}
