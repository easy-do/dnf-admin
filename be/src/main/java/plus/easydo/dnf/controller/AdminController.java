package plus.easydo.dnf.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.DpResult;
import plus.easydo.dnf.vo.R;


/**
 * @author laoyu
 * @version 1.0
 * @description 测试接口
 * @date 2023/10/25
 */
@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    @Value("${gmKey:123456789}")
    private String gmKey;

    @GetMapping("/get")
    public R<Object> roleList(
            @RequestParam("gmKey")String gmKey,
            @RequestParam("type")String type,
            @RequestParam("value")String value){
        if(!gmKey.equals(gmKey)){
            return DataResult.fail("gmKey错误,请检查配置.");
        }
        log.info("type:{},value:{}",type,value);
        return DataResult.ok(DpResult.build(type,value));
    }

}
