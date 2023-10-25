package plus.easydo.dnf.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;


/**
 * @author laoyu
 * @version 1.0
 * @description 测试接口
 * @date 2023/10/25
 */
@Slf4j
@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/test")
    public R<Object> roleList(
            @RequestParam("type")String type,
            @RequestParam("value")String value){
        log.info("type:{}",type);
        log.info("value:{}",value);
        return DataResult.ok();
    }

}
