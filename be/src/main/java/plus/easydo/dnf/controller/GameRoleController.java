package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.dto.AccountCargoDto;
import plus.easydo.dnf.dto.EditGameRoleItemDto;
import plus.easydo.dnf.dto.OtherDataDto;
import plus.easydo.dnf.entity.AccountCargo;
import plus.easydo.dnf.entity.CharacInfo;
import plus.easydo.dnf.entity.UserItems;
import plus.easydo.dnf.qo.CharacInfoQo;
import plus.easydo.dnf.service.GamePostalService;
import plus.easydo.dnf.service.GameRoleService;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.RoleItemVo;
import plus.easydo.dnf.vo.OtherDataVo;
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

    private final GamePostalService gamePostalService;

    @Operation(summary = "所有角色列表")
    @SaCheckLogin
    @GetMapping("/list")
    public R<List<CharacInfo>> roleList(@RequestParam(value = "name", required = false)String name){

        return DataResult.ok(gameRoleService.roleList(StpUtil.getLoginIdAsLong(),name));
    }

    @Operation(summary = "所有角色列表")
    @SaCheckLogin
    @GetMapping("/allList")
    public R<List<CharacInfo>> allList(@RequestParam(value = "name", required = false)String name){
        return DataResult.ok(gameRoleService.roleList(null,name));
    }

    @Operation(summary = "分页查询")
    @SaCheckPermission("gameRole")
    @PostMapping("/page")
    public R<List<CharacInfo>> gameRolePage(@RequestBody CharacInfoQo characInfoQo){
        return DataResult.ok(gameRoleService.page(characInfoQo));
    }


    @Operation(summary = "详情")
    @SaCheckPermission("gameRole")
    @GetMapping("/info/{characNo}")
    public R<CharacInfo> gameRoleInfo(@PathVariable("characNo") Long characNo){
        return DataResult.ok(gameRoleService.info(characNo));
    }

    @Operation(summary = "更新角色信息")
    @SaCheckPermission("gameRole.update")
    @PostMapping("/update")
    public R<Boolean> gameRoleUpdate(@RequestBody CharacInfo characInfo){
        return DataResult.ok(gameRoleService.update(characInfo));
    }

    @Operation(summary = "删除角色")
    @SaCheckPermission("gameRole.update")
    @GetMapping("/remove/{characNo}")
    public R<Boolean> gameRoleRemove(@PathVariable("characNo") Long characNo){
        return DataResult.ok(gameRoleService.remove(characNo));
    }

    @Operation(summary = "恢复角色")
    @SaCheckPermission("gameRole.update")
    @GetMapping("/recover/{characNo}")
    public R<Boolean> gameRoleRecover(@PathVariable("characNo") Long characNo){
        return DataResult.ok(gameRoleService.recover(characNo));
    }


    @Operation(summary = "获取角色物品")
    @SaCheckPermission("gameRole")
    @GetMapping("/getRoleItem/{characNo}/{type}")
    public R<RoleItemVo> getRoleItem(@PathVariable("characNo") Long characNo, @PathVariable("type") String type){
        return DataResult.ok(gameRoleService.getRoleItem(characNo,type));
    }

    @Operation(summary = "更新角色物品")
    @SaCheckPermission("gameRole.update")
    @PostMapping("/updateRoleItem")
    public R<Boolean> updateRoleItem(@RequestBody EditGameRoleItemDto editGameRoleItemDto){
        return DataResult.ok(gameRoleService.updateRoleItem(editGameRoleItemDto));
    }

    @Operation(summary = "清空角色物品")
    @SaCheckPermission("gameRole.update")
    @GetMapping("/cleanRoleItem/{characNo}/{type}")
    public R<Boolean> cleanRoleItem(@PathVariable("characNo") Long characNo,@PathVariable("type") Long type){
        return DataResult.ok(gameRoleService.cleanItem(characNo,type));
    }

    @Operation(summary = "开启角色左右槽")
    @SaCheckPermission("gameRole.openLeftAndRight")
    @GetMapping("/openLeftAndRight/{characNo}")
    public R<Boolean> openLeftAndRight(@PathVariable("characNo") Long characNo){
        return DataResult.ok(gameRoleService.openLeftAndRight(characNo));
    }

    @Operation(summary = "清空角色邮件")
    @SaCheckPermission("gameRole.update")
    @PostMapping("/cleanMail/{characNo}")
    public R<Boolean> cleanMail(@PathVariable("characNo") Long characNo){
        return DataResult.ok(gamePostalService.cleanCharacMail(characNo));
    }

    @Operation(summary = "获取角色其他信息")
    @SaCheckPermission("gameRole")
    @GetMapping("/getOtherData/{characNo}")
    public R<OtherDataVo> getOtherData(@PathVariable("characNo") Long characNo){
        return DataResult.ok(gameRoleService.getOtherData(characNo));
    }

    @Operation(summary = "修改角色其他信息")
    @SaCheckPermission("gameRole.update")
    @PostMapping("/setOtherData")
    public R<Boolean> setOtherData(@RequestBody OtherDataDto otherDataDto){
        return DataResult.ok(gameRoleService.setOtherData(otherDataDto));
    }

    @Operation(summary = "获取时装信息")
    @SaCheckPermission("gameRole.update")
    @GetMapping("/items/{characNo}")
    public R<List<UserItems>> roleItems(@PathVariable("characNo") Long characNo) {
        return DataResult.ok(gameRoleService.roleItems(characNo));
    }

    @Operation(summary = "删除单件时装")
    @SaCheckPermission("gameRole.update")
    @GetMapping("/removeItems/{uiId}")
    public R<Boolean> removeItems(@PathVariable("uiId") Long uiId) {
        return DataResult.ok(gameRoleService.removeItems(uiId));
    }

    @Operation(summary = "清空所有时装")
    @SaCheckPermission("gameRole.update")
    @GetMapping("/cleanItems/{characNo}")
    public R<Boolean> cleanItems(@PathVariable("characNo") Long characNo) {
        return DataResult.ok(gameRoleService.cleanItems(characNo));
    }

    @Operation(summary = "获取账号金库信息")
    @SaCheckPermission("gameRole.update")
    @GetMapping("/getAccountCargo/{characNo}")
    public R<AccountCargo> getAccountCargo(@PathVariable("characNo") Long characNo) {
        return DataResult.ok(gameRoleService.getAccountCargo(characNo));
    }

    @Operation(summary = "开通账号金库")
    @SaCheckPermission("gameRole.update")
    @GetMapping("/createAccountCargo/{characNo}")
    public R<Boolean> createAccountCargo(@PathVariable("characNo") Long characNo) {
        return DataResult.ok(gameRoleService.createAccountCargo(characNo));
    }

    @Operation(summary = "删除账号金库")
    @SaCheckPermission("gameRole.update")
    @GetMapping("/removeAccountCargo/{characNo}")
    public R<Boolean> removeAccountCargo(@PathVariable("characNo") Long characNo) {
        return DataResult.ok(gameRoleService.removeAccountCargo(characNo));
    }

    @Operation(summary = "修改账号金库信息")
    @SaCheckPermission("gameRole.update")
    @PostMapping("/updateAccountCargo")
    public R<Boolean> updateAccountCargo(@RequestBody AccountCargoDto accountCargoDto) {
        return DataResult.ok(gameRoleService.updateAccountCargo(accountCargoDto));
    }

}
