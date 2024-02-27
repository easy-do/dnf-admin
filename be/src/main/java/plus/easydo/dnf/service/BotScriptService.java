package plus.easydo.dnf.service;

import com.mybatisflex.core.paginate.Page;
import plus.easydo.dnf.entity.DaBotEventScript;
import plus.easydo.dnf.qo.DaBotEventScriptQo;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 机器人相关
 * @date 2024/2/21
 */

public interface BotScriptService {
    Page<DaBotEventScript> pageBotScript(DaBotEventScriptQo daBotEventScriptQo);

    Boolean addBotScript(DaBotEventScript daBotEventScript);

    boolean updateBotScript(DaBotEventScript daBotEventScript);

    boolean removeBotScript(List<String> ids);

    DaBotEventScript infoBotScript(Long id);

    void initBotEventScriptCache();

    List<DaBotEventScript> botScriptList();
}
