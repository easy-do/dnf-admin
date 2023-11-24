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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.constant.SystemConstant;
import plus.easydo.dnf.dto.SendMailDto;
import plus.easydo.dnf.entity.DaMailSendLog;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.service.AccountsService;
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

    private final AccountsService accountsService;



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
        return DataResult.ok();
    }

    @SaCheckPermission("tool.restartServer")
    @GetMapping("/restartServer")
    public R<String> restartServer() {
        String rs = RuntimeUtil.execForStr(CacheManager.GAME_CONF_MAP.get(SystemConstant.RESTART_SERVER).getConfData());
        return DataResult.ok(rs);
    }

    @SaCheckPermission("tool.restartDb")
    @GetMapping("/restartDb")
    public R<String> restartDb() {
        String rs = RuntimeUtil.execForStr(CacheManager.GAME_CONF_MAP.get(SystemConstant.RESTART_DB).getConfData());
        return DataResult.ok(rs);
    }

    @SaCheckPermission("tool.restartDa")
    @GetMapping("/restartDa")
    public R<String> restartAdmin() {
        CompletableFuture.runAsync(()->RuntimeUtil.exec(CacheManager.GAME_CONF_MAP.get(SystemConstant.RESTART_DA).getConfData()));
        return DataResult.ok();
    }

    @SaCheckPermission("tool.recharge")
    @GetMapping("/rechargeBonds")
    public R<String> rechargeBonds(
            @RequestParam(name = "type", defaultValue = "1") Integer type,
            @RequestParam(name = "uid") Long uid,
            @RequestParam(name = "count", defaultValue = "1") Long count ) {
        accountsService.rechargeBonds(type, uid, count);
        return DataResult.ok();
    }

    @SaCheckPermission("tool.enableAcc")
    @GetMapping("/enableAcc")
    public R<String> enableAcc(
            @RequestParam(name = "uid") Long uid) {
        accountsService.enableAcc(uid);
        return DataResult.ok();
    }

    @SaCheckPermission("tool.disableAcc")
    @GetMapping("/disableAcc")
    public R<String> disableAcc(
            @RequestParam(name = "uid") Long uid) {
        accountsService.disableAcc(uid);
        return DataResult.ok();
    }


    @SaCheckLogin
    @GetMapping("/getGameToken")
    public R<String> getGameToken() {
        // 得到待加密的用户标识
        String data = String.format("%08x0101010101010101010101010101010101010101010101010101010101010101559145100" +
                "10403030101", StpUtil.getLoginIdAsLong());
        // 加密计算出用户授权Key
        String privateKey = RSAUtils.privateKeyContentByPath("E:\\buhui70\\privatekey.pem");
        String token = RSAUtils.encryptByPrivateKey(data, privateKey);
        return DataResult.ok(token);
    }

}


