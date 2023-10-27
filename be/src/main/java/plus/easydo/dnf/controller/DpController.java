package plus.easydo.dnf.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.handler.DpReportHandler;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.DpResult;
import plus.easydo.dnf.vo.R;

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

    @Value("${gmKey:123456789}")
    private String gmKey;

    @Autowired
    private Map<String, DpReportHandler> reportHandlerMap;

    @GetMapping("/report")
    public R<Object> roleList(
            @RequestParam("gmKey")String gmKey,
            @RequestParam("type")String type,
            @RequestParam("value")String value){
        if(!this.gmKey.equals(gmKey)){
            return DataResult.fail("gmKey错误,请检查配置.");
        }
        log.info("type:{},value:{}",type,value);
        DpReportHandler handler = reportHandlerMap.get(type);
        if(Objects.nonNull(handler)){
            DataResult.ok(handler.handler(type,value));
        }
        return DataResult.ok(DpResult.build(type,value));
    }

}
