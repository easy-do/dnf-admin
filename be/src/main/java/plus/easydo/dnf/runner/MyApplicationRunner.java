package plus.easydo.dnf.runner;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RuntimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.constant.SystemConstant;
import plus.easydo.dnf.entity.DaGameConfig;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.service.IDaGameConfigService;
import plus.easydo.dnf.service.IDaItemService;
import plus.easydo.dnf.util.pvf.PvfData;
import plus.easydo.dnf.util.pvf.PvfReader;

import java.util.Map;
import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description 启动运行
 * @date 2023/11/12
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MyApplicationRunner implements ApplicationRunner {

    private final IDaItemService daItemService;

    private final IDaGameConfigService daGameConfigService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        daGameConfigService.cacheGameConf();
        DaGameConfig copyDp2Conf = CacheManager.GAME_CONF_MAP.get(SystemConstant.COPY_DP2);
        DaGameConfig dp2PthConf = CacheManager.GAME_CONF_MAP.get(SystemConstant.DP2_PATH);
        if (Boolean.parseBoolean(copyDp2Conf.getConfData())) {
            //将dp2文件copy到指定的目录
            RuntimeUtil.exec("cp -r /home/dp2/* " + dp2PthConf.getConfData());
        }
        DaGameConfig readConf = CacheManager.GAME_CONF_MAP.get(SystemConstant.READER_PVF);
        String confValue = readConf.getConfData();
        if (Boolean.parseBoolean(confValue)) {
            String pvfPath = CacheManager.GAME_CONF_MAP.get(SystemConstant.PVF_PATH).getConfData();
            if (FileUtil.isFile(pvfPath)) {
                try {
                    PvfData pvfData = PvfReader.reader(pvfPath);
                    Map<Integer, String> itemMap = pvfData.getItemMap();
                    daItemService.importItemForMap(itemMap);
                    readConf.setConfData("false");
                    daGameConfigService.updateById(readConf);
                } catch (Exception exception) {
                    log.warn("解析pvf文件发生错误{}", ExceptionUtil.getMessage(exception));
                }
            } else {
                log.warn("在路径{}下没有找到pvf文件,请检查配置", pvfPath);
            }
        }
        System.gc();
    }
}
