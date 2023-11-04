package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.dto.SendMailDto;
import plus.easydo.dnf.entity.DaMailSendLogEntity;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.service.IDaMailSendLogService;
import plus.easydo.dnf.util.ExecCallBuildUtil;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.time.LocalDateTime;

/**
 * @author laoyu
 * @version 1.0
 * @description 游戏工具
 * @date 2023/10/29
 */
@RestController
@RequestMapping("/api/gameTool")
@RequiredArgsConstructor
public class GameToolController {

    private final IDaMailSendLogService iDaMailSendLogService;

    @SaCheckRole("admin")
    @PostMapping("/sendMail")
    public R<Object> sendMail(@RequestBody SendMailDto sendMailDto){
        CacheManager.addFirstOptExecList(ExecCallBuildUtil.buildSendMultiMail(sendMailDto));
        iDaMailSendLogService.save(DaMailSendLogEntity.builder().sendDetails(JSONUtil.toJsonStr(sendMailDto)).createTime(LocalDateTime.now()).build());
        return DataResult.ok();
    }

}


