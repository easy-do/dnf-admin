package plus.easydo.dnf.service.impl;

import cn.hutool.core.lang.UUID;
import com.mybatisflex.core.paginate.Page;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.dto.EnableBotScriptDto;
import plus.easydo.dnf.entity.*;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.manager.*;
import plus.easydo.dnf.qo.DaBotMessageQo;
import plus.easydo.dnf.qo.DaBotNoticeQo;
import plus.easydo.dnf.qo.DaBotQo;
import plus.easydo.dnf.qo.DaBotRequestQo;
import plus.easydo.dnf.service.BotScriptService;
import plus.easydo.dnf.service.BotService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author laoyu
 * @version 1.0
 * @description 机器人相关实现
 * @date 2024/2/21
 */
@Service
@RequiredArgsConstructor
public class BotServiceImpl implements BotService {

    private final DaBotInfoManager daBotInfoManager;

    private final DaBotConfManager daBotConfManager;

    private final DaBotMessageManager daBotMessageManager;

    private final DaBotRequestManager daBotRequestManager;

    private final DaBotNoticeManager daBotNoticeManager;

    private final DaBotScriptBotManager daBotScriptBotManager;

    private final BotScriptService botScriptService;

    @Override
    public Page<DaBotInfo> pageBot(DaBotQo daBotQo) {
        return daBotInfoManager.page(new Page<>(daBotQo.getCurrent(), daBotQo.getPageSize()));
    }

    @Override
    public Boolean addBot(DaBotInfo daBotInfo) {
        daBotInfo.setBotSecret(UUID.fastUUID().toString(true));
        boolean res = daBotInfoManager.save(daBotInfo);
        if (res) {
            initBotCache();
        }
        return res;
    }

    @Override
    public boolean updateBot(DaBotInfo daBotInfo) {
        boolean res = daBotInfoManager.updateById(daBotInfo);
        if (res) {
            initBotCache();
        }
        return res;
    }

    @Override
    public boolean removeBot(List<String> ids) {
        boolean res = daBotInfoManager.removeByIds(ids);
        if (res) {
            initBotCache();
        }
        return res;
    }

    @PostConstruct
    @Override
    public void initBotCache() {
        Map<String, DaBotInfo> map = daBotInfoManager.list().stream().collect(Collectors.toMap(DaBotInfo::getBotNumber, (c) -> c));
        CacheManager.BOT_CACHE.putAll(map);
    }

    @PostConstruct
    @Override
    public void initBotConfCache() {
        List<DaBotConf> allConf = daBotConfManager.list();
        Map<String,Map<String,String>> cacheMap = new HashMap<>();
        Map<String, List<DaBotConf>> botNumberMap = allConf.stream().collect(Collectors.groupingBy(DaBotConf::getBotNumber));
        for (Map.Entry<String, List<DaBotConf>> entry :botNumberMap.entrySet()){
            String platformBotNumber = entry.getKey();
            List<DaBotConf> values = entry.getValue();
            Map<String, String> confMap = values.stream().collect(Collectors.toMap(DaBotConf::getConfKey, DaBotConf::getConfValue));
            cacheMap.put(platformBotNumber,confMap);
        }
       CacheManager.BOT_CONF_CACHE.putAll(cacheMap);
    }

    @Override
    public Page<DaBotMessage> pageBotMessage(DaBotMessageQo daBotMessageQo) {
        return daBotMessageManager.page(new Page<>(daBotMessageQo.getCurrent(), daBotMessageQo.getPageSize()));
    }

    @Override
    public Page<DaBotRequest> pageBotRequest(DaBotRequestQo daBotRequestQo) {
        return daBotRequestManager.page(new Page<>(daBotRequestQo.getCurrent(), daBotRequestQo.getPageSize()));
    }

    @Override
    public Page<DaBotNotice> pageBotNotice(DaBotNoticeQo daBotNoticeQo) {
        return daBotNoticeManager.page(new Page<>(daBotNoticeQo.getCurrent(), daBotNoticeQo.getPageSize()));
    }

    @Override
    public List<DaBotConf> getBotConf(String botNumber) {
        return daBotConfManager.getByBotNumber(botNumber);
    }

    @Override
    public boolean updateBotConf(DaBotConf daBotConf) {
        boolean res = daBotConfManager.updateById(daBotConf);
        if(res){
            initBotConfCache();
        }
        return res;
    }

    @Override
    public boolean addBotConf(DaBotConf daBotConf) {
        DaBotConf dbConf = daBotConfManager.getByBotNumberAndKey(daBotConf.getBotNumber(), daBotConf.getConfKey());
        if(Objects.nonNull(dbConf)){
            daBotConf.setId(dbConf.getId());
            daBotConf.setConfValue(daBotConf.getConfValue());
            return updateBotConf(daBotConf);
        }

        boolean res = daBotConfManager.save(daBotConf);
        if(res){
            initBotConfCache();
        }
        return res;
    }

    @Override
    public boolean removeBotConf(Long id) {
        return daBotConfManager.removeById(id);
    }

    @Override
    public DaBotConf getByBotNumberAndKey(String botNumber, String key) {
        return daBotConfManager.getByBotNumberAndKey(botNumber,key);
    }

    @Override
    public List<Long> getEnableBotScript(Long id) {
        DaBotInfo botInfo = daBotInfoManager.getById(id);
        if(Objects.isNull(botInfo)){
            throw new BaseException("机器人不存在");
        }
        return daBotScriptBotManager.getBotScript(botInfo.getBotNumber()).stream().map(DaBotScriptBot::getScriptId).toList();
    }

    @Override
    public boolean enableBotScript(EnableBotScriptDto enableBotScriptDto) {
        DaBotInfo botInfo = daBotInfoManager.getById(enableBotScriptDto.getBotId());
        boolean res;
        if(Objects.isNull(botInfo)){
            throw new BaseException("机器人不存在");
        }
        if(Objects.isNull(enableBotScriptDto.getScriptIds()) || enableBotScriptDto.getScriptIds().isEmpty()){
            res =  daBotScriptBotManager.clearBotScript(botInfo.getBotNumber());
        }
        res = daBotScriptBotManager.saveBotScript(botInfo.getBotNumber(), enableBotScriptDto.getScriptIds());
        if(res){
            botScriptService.initBotEventScriptCache();
        }
        return res;
    }

    @Override
    public DaBotInfo infoBot(Long id) {
        return daBotInfoManager.getById(id);
    }
}
