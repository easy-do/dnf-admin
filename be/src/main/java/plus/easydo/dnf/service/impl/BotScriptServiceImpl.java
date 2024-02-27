package plus.easydo.dnf.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.mybatisflex.core.paginate.Page;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.entity.DaBotEventScript;
import plus.easydo.dnf.entity.DaBotScriptBot;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.manager.DaBotEventScriptManager;
import plus.easydo.dnf.manager.DaBotScriptBotManager;
import plus.easydo.dnf.qo.DaBotEventScriptQo;
import plus.easydo.dnf.service.BotScriptService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author laoyu
 * @version 1.0
 * @description 机器人相关
 * @date 2024/2/21
 */
@Service
@RequiredArgsConstructor
public class BotScriptServiceImpl implements BotScriptService {

    private final DaBotEventScriptManager botEventScriptManager;

    private final DaBotEventScriptManager daBotEventScriptManager;

    private final DaBotScriptBotManager daBotScriptBotManager;

    @Override
    public Page<DaBotEventScript> pageBotScript(DaBotEventScriptQo daBotEventScriptQo) {
        return botEventScriptManager.pageBotScript(daBotEventScriptQo);
    }

    @Override
    public Boolean addBotScript(DaBotEventScript daBotEventScript) {
        return botEventScriptManager.save(daBotEventScript);
    }

    @Override
    public boolean updateBotScript(DaBotEventScript daBotEventScript) {
        boolean res = botEventScriptManager.updateById(daBotEventScript);
        if(res && CharSequenceUtil.isNotBlank(daBotEventScript.getScriptContent())){
            initBotEventScriptCache();
        }
        return res;
    }

    @Override
    public boolean removeBotScript(List<String> ids) {
        return botEventScriptManager.removeByIds(ids);
    }

    @Override
    public DaBotEventScript infoBotScript(Long id) {
        return botEventScriptManager.getById(id);
    }

    @PostConstruct
    @Override
    public void initBotEventScriptCache() {
        //缓存脚本
        List<DaBotEventScript> list = daBotEventScriptManager.list();
        //按事件类型分组
        Map<String, List<DaBotEventScript>> eventScriptGroup = list.stream().collect(Collectors.groupingBy(DaBotEventScript::getEventType));
        //事件与脚本id关联关系
        Map<String,List<Long>> eventScriptIdMap = new HashMap<>();
        eventScriptGroup.forEach((key,value)-> eventScriptIdMap.put(key,value.stream().map(DaBotEventScript::getId).toList()));
        CacheManager.EVENT_SCRIPT_ID_CACHE.putAll(eventScriptIdMap);
        //bot的所有脚本
        List<DaBotScriptBot> allBf = daBotScriptBotManager.list();
        //按机器人编号分组
        Map<String, List<DaBotScriptBot>> botScriptGroup = allBf.stream().collect(Collectors.groupingBy(DaBotScriptBot::getBotNumber));
        //机器人与脚本的对应关系
        Map<String,List<Long>> botScriptIdMap = new HashMap<>();
        botScriptGroup.forEach((key,value)-> botScriptIdMap.put(key,value.stream().map(DaBotScriptBot::getScriptId).toList()));
        CacheManager.BOT_SCRIPT_ID_CACHE.putAll(botScriptIdMap);
        //所有脚本缓存
        CacheManager.EVENT_SCRIPT_CACHE.putAll(list.stream().collect(Collectors.toMap(DaBotEventScript::getId, fun -> fun)));
    }

    @Override
    public List<DaBotEventScript> botScriptList() {
        return botEventScriptManager.listAll();
    }
}
