package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.util.URLUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.enums.CallFunEnum;
import plus.easydo.dnf.handler.DpReportHandler;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.vo.CallResult;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;


/**
 * @author laoyu
 * @version 1.0
 * @description dp相关接口
 * @date 2023/10/25
 */
@Slf4j
@RestController
@RequestMapping("/api/dp")
@RequiredArgsConstructor
public class DpController {

    @Value("${dpGmKey:123456789}")
    private String dpGmKey;

    @Autowired
    private Map<String, DpReportHandler> reportHandlerMap;

    @GetMapping("/report")
    public R<Object> roleList(
            @RequestParam("gmKey")String gmKey,
            @RequestParam("type")String type,
            @RequestParam("value")String value){
        if(!this.dpGmKey.equals(gmKey)){
            return DataResult.fail("gmKey错误,请检查配置.");
        }
        value = URLUtil.decode(value);
        DpReportHandler handler = reportHandlerMap.get(type);
        if(Objects.nonNull(handler)){
            return DataResult.ok(handler.handler(type,value));
        }
        return DataResult.fail(type + " handler not found");
    }
}
