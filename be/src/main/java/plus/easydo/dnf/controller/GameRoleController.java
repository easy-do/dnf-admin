package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.entity.CharacInfo;
import plus.easydo.dnf.service.GameRoleService;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 游戏角色相关
 * @date 2023/10/14
 */
@RestController
@RequestMapping("/api/gameRole")
@RequiredArgsConstructor
public class GameRoleController {

    private final GameRoleService gameRoleService;

    @SaCheckLogin
    @GetMapping("/list")
    public R<List<CharacInfo>> roleList(@RequestParam(value = "name", required = false)String name){

        return DataResult.ok(gameRoleService.roleList(StpUtil.getLoginIdAsInt(),name));
    }
    @SaCheckLogin
    @GetMapping("/allList")
    public R<List<CharacInfo>> allList(@RequestParam(value = "name", required = false)String name){
        return DataResult.ok(gameRoleService.roleList(null,name));
    }
}
