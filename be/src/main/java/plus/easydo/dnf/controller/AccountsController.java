package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.dto.RegDto;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.qo.AccountsQo;
import plus.easydo.dnf.service.AccountsService;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.util.List;


/**
 * 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountsController {


    private final AccountsService accountsService;

    /**
     * 添加
     *
     * @param regDto
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @Operation(summary = "添加账户")
    @SaCheckPermission("accounts.save")
    @PostMapping("/save")
    public R<Long> saveAccounts(@RequestBody RegDto regDto) {
        return DataResult.ok(accountsService.regAcc(regDto));
    }


    /**
     * 根据主键更新
     *
     * @param accounts
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @Operation(summary = "编辑账户")
    @SaCheckPermission("accounts.update")
    @PostMapping("/update")
    public R<Boolean> updateAccounts(@RequestBody Accounts accounts) {
        return DataResult.ok(accountsService.update(accounts));
    }


    /**
     * 根据主键获取详细信息。
     *
     * @param id accounts主键
     * @return 详情
     */
    @Operation(summary = "账户详情")
    @SaCheckPermission("accounts")
    @GetMapping("/info/{id}")
    public  R<Accounts> getAccounts(@PathVariable Long id) {
        return DataResult.ok(accountsService.getById(id));
    }


    /**
     * 分页查询
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @Operation(summary = "分页")
    @SaCheckPermission("accounts")
    @PostMapping("/page")
    public R<List<Accounts>> pageAccounts(AccountsQo page) {
        return DataResult.ok(accountsService.page(page));
    }

    /**
     * 解封账号
     *
     * @param uid uid
     * @return plus.easydo.dnf.vo.R<java.lang.String>
     * @author laoyu
     */
    @Operation(summary = "解封")
    @SaCheckPermission("accounts.enable")
    @GetMapping("/enable/{uid}")
    public R<Boolean> enableAccounts(
            @PathVariable(name = "uid") Long uid) {
        return DataResult.ok(accountsService.enableAcc(uid));
    }

    /**
     * 封禁账号
     *
     * @param uid uid
     * @return plus.easydo.dnf.vo.R<java.lang.String>
     * @author laoyu
     */
    @Operation(summary = "封禁")
    @SaCheckPermission("accounts.disable")
    @GetMapping("/disable/{uid}")
    public R<Boolean> disableAccounts(
            @PathVariable(name = "uid") Long uid) {
        return DataResult.ok(accountsService.disableAcc(uid));
    }

    /**
     * 重置密码
     *
     * @param uid uid
     * @param password password
     * @return plus.easydo.dnf.vo.R<java.lang.Boolean>
     * @author laoyu
     */
    @Operation(summary = "重置密码")
    @SaCheckPermission("accounts.resetPass")
    @GetMapping("/resetPass/{uid}")
    public R<Boolean> resetPassword(
            @PathVariable(name = "uid") Long uid,
            @RequestParam(name = "password") String password) {
        return DataResult.ok(accountsService.resetPassword(uid,password));
    }


    /**
     * 点券充值
     *
     * @param type type
     * @param uid uid
     * @param count count
     * @return plus.easydo.dnf.vo.R<java.lang.String>
     * @author laoyu
     * @date 2023-12-01
     */
    @Operation(summary = "点券充值")
    @SaCheckPermission("accounts.recharge")
    @GetMapping("/rechargeBonds")
    public R<String> rechargeBonds(
            @RequestParam(name = "type", defaultValue = "1") Integer type,
            @RequestParam(name = "uid") Long uid,
            @RequestParam(name = "count", defaultValue = "1") Long count ) {
        accountsService.rechargeBonds(type, uid, count);
        return DataResult.ok();
    }

    /**
     * 重置角色创建限制
     *
     * @param uid uid
     * @return plus.easydo.dnf.vo.R<java.lang.String>
     * @author laoyu
     * @date 2024/1/7
     */
    @Operation(summary = "重置角色创建限制")
    @SaCheckPermission("accounts.resetCreateRole")
    @GetMapping("/resetCreateRole/{uid}")
    public R<Boolean> resetCreateRole(@PathVariable(name = "uid") Long uid) {
        return DataResult.ok(accountsService.resetCreateRole(uid));
    }

    /**
     * 设置角色栏最大
     *
     * @param uid uid
     * @return plus.easydo.dnf.vo.R<java.lang.String>
     * @author laoyu
     * @date 2024/1/7
     */
    @Operation(summary = "设置角色栏最大")
    @SaCheckPermission("accounts.setMaxRole")
    @GetMapping("/setMaxRole/{uid}")
    public R<Boolean> setMaxRole(@PathVariable(name = "uid") Long uid) {
        return DataResult.ok(accountsService.setMaxRole(uid));
    }
    /**
     * 开启全图王者
     *
     * @param uid uid
     * @return plus.easydo.dnf.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024-01-31
     */
    @Operation(summary = "开启全图王者")
    @SaCheckPermission("accounts.openDungeon")
    @GetMapping("/openDungeon/{uid}")
    public R<Boolean> openDungeon(@PathVariable(name = "uid") Long uid) {
        return DataResult.ok(accountsService.openDungeon(uid));
    }

}
