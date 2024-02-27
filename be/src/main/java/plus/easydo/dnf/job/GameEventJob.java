package plus.easydo.dnf.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.config.SystemConfig;
import plus.easydo.dnf.constant.SystemConstant;
import plus.easydo.dnf.entity.DaGameEvent;
import plus.easydo.dnf.enums.SysTemModeEnum;
import plus.easydo.dnf.service.DaGameEventService;
import plus.easydo.dnf.util.HistoryLogReaderUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author laoyu
 * @version 1.0
 * @description 定时读取游戏日志
 * @date 2024/2/1
 */
@Slf4j
@Component
public class GameEventJob {

    @Autowired
    private DaGameEventService daGameEventService;

    @Autowired
    private SystemConfig systemConfig;

    @Scheduled(fixedDelay = 1000 * 30)
    public void readerHistoryLog() {
        if(!SysTemModeEnum.UTILS.getMode().equals(systemConfig.getMode())){
            log.debug("开始读取游戏事件数据");
            Map<String, Integer> index = daGameEventService.getMaxFileIndex();
            List<DaGameEvent> logs = HistoryLogReaderUtil.readerHistoryLog(SystemConstant.LOG_PATH);
            for (DaGameEvent daGameEvent : logs){
                Integer max = index.get(daGameEvent.getFileName());
                if(Objects.isNull(max) || daGameEvent.getFileIndex() > max){
                    daGameEventService.save(daGameEvent);
                }
            }
            log.debug("读取游戏事件数据结束");
        }
    }
}
