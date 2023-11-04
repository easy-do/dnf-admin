package plus.easydo.dnf.handler;

import org.springframework.stereotype.Service;
import plus.easydo.dnf.constant.ReportTypeConstant;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.vo.DpResult;

import java.util.HashMap;
import java.util.Map;

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
        Map<String,String> resultMap = new HashMap<>();
        CacheManager.GAME_CONF_LIST.forEach(gameConfig->{
            resultMap.put(gameConfig.getConfKey(),gameConfig.getConfData());
        });
        return DpResult.build(ReportTypeConstant.GET_CONF, resultMap);
    }
}
