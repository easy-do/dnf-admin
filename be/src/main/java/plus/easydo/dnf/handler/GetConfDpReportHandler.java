package plus.easydo.dnf.handler;

import org.springframework.stereotype.Service;
import plus.easydo.dnf.constant.ReportTypeConstant;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.vo.DpResult;


/**
 * @author laoyu
 * @version 1.0
 * @description 获取配置处理
 * @date 2023/11/4
 */
@Service(ReportTypeConstant.GET_CONF)
public class GetConfDpReportHandler implements DpReportHandler{
    @Override
    public DpResult handler(String opt, String value) {
        return DpResult.build(ReportTypeConstant.GET_CONF, CacheManager.GAME_CONF_LIST);
    }
}
