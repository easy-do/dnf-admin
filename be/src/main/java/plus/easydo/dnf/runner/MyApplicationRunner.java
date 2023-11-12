package plus.easydo.dnf.runner;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.entity.DaGameConfigEntity;
import plus.easydo.dnf.service.IDaGameConfigService;
import plus.easydo.dnf.service.IDaItemService;
import plus.easydo.dnf.util.ItemReaderUtil;
import plus.easydo.dnf.util.pvf.PvfData;
import plus.easydo.dnf.util.pvf.PvfReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Value("${pvfPath:/data/server/data/Script.pvf}")
    private String pvfPath;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        DaGameConfigEntity conf = daGameConfigService.getByConfKey("gm_reader_pvf");
        String confValue = conf.getConfData();
        if(Boolean.parseBoolean(confValue)){
            if(FileUtil.isFile(pvfPath)){
                try {
                    List<JSONObject> jsonObjectList = new ArrayList<>();
                    PvfData pvfData = PvfReader.reader(pvfPath);
                    Map<Integer, String> itemMap = pvfData.getItemMap();
                    itemMap.forEach((key,value)->{
                        JSONObject res = ItemReaderUtil.readerForStr(value);
                        res.set("itemId",key);
                        jsonObjectList.add(res);
                    });
                    daItemService.importItemForJson(jsonObjectList);
                    conf.setConfData("false");
                    daGameConfigService.updateById(conf);
                }catch (Exception exception){
                    log.warn("解析pvf文件发生错误{}", ExceptionUtil.getMessage(exception));
                }

            }else {
                log.warn("在路径{}下没有找到pvf文件,请检查配置",pvfPath);
            }
        }
    }
}
