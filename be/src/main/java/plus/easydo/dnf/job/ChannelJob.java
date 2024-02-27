package plus.easydo.dnf.job;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.config.SystemConfig;
import plus.easydo.dnf.constant.SystemConstant;
import plus.easydo.dnf.entity.DaChannel;
import plus.easydo.dnf.entity.DaGameConfig;
import plus.easydo.dnf.enums.SysTemModeEnum;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.service.IDaChannelService;
import plus.easydo.dnf.util.DockerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description 频道任务
 * @date 2023/12/2
 */
@Slf4j
@Component
public class ChannelJob {

    @Autowired
    private IDaChannelService daChannelService;

    @Autowired
    private SystemConfig systemConfig;

    @Scheduled(fixedDelay = 1000 * 30)
    public void syncChannelStatus() {
        if(!SysTemModeEnum.UTILS.getMode().equals(systemConfig.getMode())){
            log.debug("开始同步频道信息");
            try {
                DaGameConfig conf = CacheManager.GAME_CONF_MAP.get(SystemConstant.FRIDA_JSON_DEFAULT_VALUE);
                if(!Objects.isNull(conf)){
                    //查询更新频道信息
                    List<DaChannel> allChannel = daChannelService.list();
                    Map<String, String> channelMap = DockerUtil.getGamePid();
                    if(allChannel.isEmpty()){
                        channelMap.forEach((key,value)->{
                            DaChannel channelEntity = new DaChannel();
                            channelEntity.setChannelName(key);
                            channelEntity.setPid(value);
                            channelEntity.setChannelStatus(true);
                            channelEntity.setFridaJsonContext(conf.getConfData());
                            daChannelService.save(channelEntity);
                        });
                    }else {
                        List<DaChannel> updateEntityList = new ArrayList<>();
                        allChannel.forEach(daChannel -> {
                            DaChannel channelEntity = new DaChannel();
                            channelEntity.setId(daChannel.getId());
                            if(channelMap.containsKey(daChannel.getChannelName())){
                                String pid = channelMap.get(daChannel.getChannelName());
                                channelEntity.setPid(pid);
                                channelEntity.setChannelStatus(true);
                                channelMap.remove(daChannel.getChannelName());
                            }else {
                                channelEntity.setChannelStatus(false);
                            }
                            updateEntityList.add(channelEntity);
                        });
                        channelMap.forEach((key,value)->{
                            DaChannel channelEntity = new DaChannel();
                            channelEntity.setChannelName(key);
                            channelEntity.setPid(value);
                            channelEntity.setChannelStatus(true);
                            channelEntity.setFridaJsonContext(conf.getConfData());
                            updateEntityList.add(channelEntity);
                        });

                        daChannelService.saveOrUpdateBatch(updateEntityList);
                    }
                }else {
                    log.info("配置文件尚未初始化完成,跳过同步");
                }
            }catch (Exception e){
                log.warn("同步频道信息失败,{}", ExceptionUtil.getMessage(e));
            }
            log.debug("同步频道信息结束");
        }
    }

    @Scheduled(fixedDelay = 1000 * 15)
    public void syncChannelFrida() {
        if(!SysTemModeEnum.UTILS.getMode().equals(systemConfig.getMode())){
            log.debug("开始同步频道frida客户端信息");
            try {
                //查询更新频道信息
                List<DaChannel> allChannel = daChannelService.list();
                List<String> names = DockerUtil.getFridaClientRunIds();
                List<DaChannel> updateEntityList = new ArrayList<>();

                allChannel.forEach(daChannel -> {
                    DaChannel channelEntity = new DaChannel();
                    channelEntity.setId(daChannel.getId());
                    String fridaClient = daChannel.getFridaClient();
                    channelEntity.setFridaStatus(CharSequenceUtil.isNotBlank(fridaClient) && names.contains(fridaClient));
                    updateEntityList.add(channelEntity);
                });
                daChannelService.updateBatch(updateEntityList);
            }catch (Exception e){
                log.warn("同步频道frida客户端信息失败,{}", ExceptionUtil.getMessage(e));
            }
            log.debug("同步频道frida客户端信息结束");
        }
    }

}
