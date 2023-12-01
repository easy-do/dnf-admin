package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.constant.SystemConstant;
import plus.easydo.dnf.dto.SendMailDto;
import plus.easydo.dnf.entity.DaMailSendLog;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.service.IDaMailSendLogService;
import plus.easydo.dnf.util.ExecCallBuildUtil;
import plus.easydo.dnf.util.RSAUtils;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

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


    @SaCheckPermission("mail.sendMail")
    @PostMapping("/sendMail")
    public R<Object> sendMail(@RequestBody SendMailDto sendMailDto) {
        CacheManager.addFirstOptExecList(ExecCallBuildUtil.buildSendMultiMail(sendMailDto));
        iDaMailSendLogService.save(DaMailSendLog.builder().sendDetails(JSONUtil.toJsonStr(sendMailDto)).createTime(LocalDateTime.now()).build());
        return DataResult.ok();
    }

    @SaCheckPermission("tool.generateKeyPem")
    @GetMapping("/generateKeyPem")
    public R<String> generateKeyPem() {
        RSAUtils.generatePemFile(CacheManager.GAME_CONF_MAP.get(SystemConstant.PEM_PATH).getConfData());
        restartServer();
        CompletableFuture.runAsync(this::restartAdmin);
        return DataResult.okMsg("生成密钥完成,将重启服务端和后台.");
    }

    @SaCheckPermission("tool.restartServer")
    @GetMapping("/restartServer")
    public R<String> restartServer() {
        RuntimeUtil.execForStr(CacheManager.GAME_CONF_MAP.get(SystemConstant.RESTART_SERVER).getConfData());
        return DataResult.okMsg("重启命令执行完成.");
    }

    @SaCheckPermission("tool.restartDb")
    @GetMapping("/restartDb")
    public R<String> restartDb() {
        RuntimeUtil.execForStr(CacheManager.GAME_CONF_MAP.get(SystemConstant.RESTART_DB).getConfData());
        return DataResult.okMsg("重启数据库命令执行完成.");
    }

    @SaCheckPermission("tool.restartDa")
    @GetMapping("/restartDa")
    public R<String> restartAdmin() {
        CompletableFuture.runAsync(()->RuntimeUtil.exec(CacheManager.GAME_CONF_MAP.get(SystemConstant.RESTART_DA).getConfData()));
        return DataResult.okMsg("重启后台命令执行完成,请注意刷新网页.");
    }


    @SaCheckLogin
    @GetMapping("/getGameToken")
    public R<String> getGameToken() {
        String data = String.format("%08x0101010101010101010101010101010101010101010101010101010101010101559145100" +
                "10403030101", StpUtil.getLoginIdAsLong());
        String privateKey = RSAUtils.privateKeyContentByPath(CacheManager.GAME_CONF_MAP.get(SystemConstant.PEM_PATH).getConfData());
        String token = RSAUtils.encryptByPrivateKey(data, privateKey);
        return DataResult.ok(token);
    }

}


