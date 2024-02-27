package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RuntimeUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.constant.SystemConstant;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.service.AccountsService;
import plus.easydo.dnf.service.GamePostalService;
import plus.easydo.dnf.service.GameRoleService;
import plus.easydo.dnf.util.RSAUtils;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.OnlineCountVo;
import plus.easydo.dnf.vo.R;

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

    private final AccountsService accountsService;

    private final GameRoleService gameRoleService;

    private final GamePostalService gamePostalService;


    @Operation(summary = "生成并更新密钥对")
    @SaCheckPermission("tool.generateKeyPem")
    @GetMapping("/generateKeyPem")
    public R<String> generateKeyPem() {
        RSAUtils.generatePemFile(CacheManager.GAME_CONF_MAP.get(SystemConstant.PEM_PATH).getConfData());
        restartServer();
        CompletableFuture.runAsync(this::restartAdmin);
        return DataResult.okMsg("生成密钥完成,将重启服务端和后台.");
    }

    @Operation(summary = "重启服务端")
    @SaCheckPermission("tool.restartServer")
    @GetMapping("/restartServer")
    public R<String> restartServer() {
        RuntimeUtil.execForStr(CacheManager.GAME_CONF_MAP.get(SystemConstant.RESTART_SERVER).getConfData());
        return DataResult.okMsg("重启命令执行完成.");
    }

    @Operation(summary = "重启数据库")
    @SaCheckPermission("tool.restartDb")
    @GetMapping("/restartDb")
    public R<String> restartDb() {
        RuntimeUtil.execForStr(CacheManager.GAME_CONF_MAP.get(SystemConstant.RESTART_DB).getConfData());
        return DataResult.okMsg("重启数据库命令执行完成.");
    }

    @Operation(summary = "重启后台")
    @SaCheckPermission("tool.restartDa")
    @GetMapping("/restartDa")
    public R<String> restartAdmin() {
        CompletableFuture.runAsync(()->RuntimeUtil.exec(CacheManager.GAME_CONF_MAP.get(SystemConstant.RESTART_DA).getConfData()));
        return DataResult.okMsg("重启后台命令执行完成,请注意刷新网页.");
    }

    @Operation(summary = "获取游戏登录token")
    @SaCheckPermission("tool.startGame")
    @GetMapping("/getGameToken")
    public R<String> getGameToken() {
        String data = String.format("%08x0101010101010101010101010101010101010101010101010101010101010101559145100" +
                "10403030101", StpUtil.getLoginIdAsLong());
        String privateKey = RSAUtils.privateKeyContentByPath(CacheManager.GAME_CONF_MAP.get(SystemConstant.PEM_PATH).getConfData());
        String token = RSAUtils.encryptByPrivateKey(data, privateKey);
        return DataResult.ok(token);
    }

    @Operation(summary = "在线用户数量")
    @GetMapping("/onlineCount")
    public R<OnlineCountVo> onlineCount() {
        OnlineCountVo onlineCountVo = new OnlineCountVo();
        onlineCountVo.setCount(accountsService.count());
        onlineCountVo.setOnline(gameRoleService.onlineCount());
        return DataResult.ok(onlineCountVo);
    }

    @Operation(summary = "清空全服邮件")
    @SaCheckPermission("tool.cleanMail")
    @GetMapping("/cleanMail")
    public R<Boolean> cleanMail() {
        return DataResult.ok(gamePostalService.cleanMail());
    }

}


