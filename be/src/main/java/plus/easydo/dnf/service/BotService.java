package plus.easydo.dnf.service;

import com.mybatisflex.core.paginate.Page;
import plus.easydo.dnf.dto.EnableBotScriptDto;
import plus.easydo.dnf.entity.DaBotConf;
import plus.easydo.dnf.entity.DaBotInfo;
import plus.easydo.dnf.entity.DaBotMessage;
import plus.easydo.dnf.entity.DaBotNotice;
import plus.easydo.dnf.entity.DaBotRequest;
import plus.easydo.dnf.qo.DaBotMessageQo;
import plus.easydo.dnf.qo.DaBotNoticeQo;
import plus.easydo.dnf.qo.DaBotQo;
import plus.easydo.dnf.qo.DaBotRequestQo;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 机器人相关
 * @date 2024/2/21
 */

public interface BotService {
    Page<DaBotInfo> pageBot(DaBotQo daBotQo);

    Boolean addBot(DaBotInfo daBotInfo);

    boolean updateBot(DaBotInfo daBotInfo);

    boolean removeBot(List<String> ids);

    void initBotCache();

    DaBotInfo infoBot(Long id);

    void initBotConfCache();

    Page<DaBotMessage> pageBotMessage(DaBotMessageQo daBotMessageQo);

    Page<DaBotRequest> pageBotRequest(DaBotRequestQo daBotRequestQo);

    Page<DaBotNotice> pageBotNotice(DaBotNoticeQo daBotNoticeQo);

    List<DaBotConf> getBotConf(String botNumber);

    boolean updateBotConf(DaBotConf daBotConf);

    boolean addBotConf(DaBotConf daBotConf);

    boolean removeBotConf(Long id);

    DaBotConf getByBotNumberAndKey(String botNumber, String key);

    List<Long> getEnableBotScript(Long id);

    boolean enableBotScript(EnableBotScriptDto enableBotScriptDto);
}
