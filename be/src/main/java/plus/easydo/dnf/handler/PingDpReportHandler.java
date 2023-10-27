package plus.easydo.dnf.handler;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.constant.ReportTypeConstant;
import plus.easydo.dnf.dto.UerLoginOutDto;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.vo.DpResult;

import java.util.Collection;
import java.util.Collections;

/**
 * @author laoyu
 * @version 1.0
 * @description ping事件处理
 * @date 2023/10/27
 */
@Service(ReportTypeConstant.PING)
public class PingDpReportHandler implements DpReportHandler {
    @Override
    public DpResult handler(String type, String value) {
        CacheManager.DP_PING_CACHE.set(0, LocalDateTimeUtil.format(LocalDateTimeUtil.now(), DatePattern.NORM_DATETIME_MINUTE_FORMATTER));
        return DpResult.build(ReportTypeConstant.PING, Collections.emptyList());
    }
}
