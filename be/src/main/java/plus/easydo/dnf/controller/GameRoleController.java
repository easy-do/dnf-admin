package plus.easydo.dnf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.service.GameRoleService;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

/**
 * @author laoyu
 * @version 1.0
 * @description 游戏角色相关
 * @date 2023/10/14
 */
@RestController
@RequestMapping("/api/game/role")
@RequiredArgsConstructor
public class GameRoleController {

    private final GameRoleService gameRoleService;
    @GetMapping("/roleList")
    public R<String> roleList(){
        gameRoleService.roleList();
        return DataResult.ok();
    }
}
