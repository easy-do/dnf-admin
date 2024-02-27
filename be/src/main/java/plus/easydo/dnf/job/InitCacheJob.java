package plus.easydo.dnf.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.service.BotScriptService;
import plus.easydo.dnf.service.BotService;
import plus.easydo.dnf.service.GameRoleService;
import plus.easydo.dnf.service.IDaItemService;


/**
 * @author laoyu
 * @version 1.0
 * @description 定时缓存角色和装备信息
 * @date 2024/2/1
 */
@Slf4j
@Component
public class InitCacheJob {

    @Autowired
    private IDaItemService daItemService;

    @Autowired
    private GameRoleService gameRoleService;

    @Autowired
    private BotService botService;

    @Autowired
    private BotScriptService botScriptService;

    @Scheduled(fixedDelay = 1000 * 60)
    public void syncChannelFrida() {
        log.debug("更新装备和角色缓存");
        daItemService.initItemCache();
        gameRoleService.initCharacCache();
        botService.initBotCache();
        botService.initBotConfCache();
        botScriptService.initBotEventScriptCache();
        log.debug("更新装备和角色缓存结束");
    }
}
