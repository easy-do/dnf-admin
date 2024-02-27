package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.dto.DaSignInConfDto;
import plus.easydo.dnf.entity.DaSignInConf;
import plus.easydo.dnf.qo.DaSignInConfQo;
import plus.easydo.dnf.service.SignInService;
import plus.easydo.dnf.vo.DaSignInConfVo;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 签到相关
 * @date 2023/10/14
 */
@RestController
@RequestMapping("/api/signIn")
@RequiredArgsConstructor
public class SignInController {

    private final SignInService signInService;

    @Operation(summary = "获取角色签到列表")
    @SaCheckLogin
    @GetMapping("/signInList/{characNo}")
    public R<List<DaSignInConfVo>> signList(@PathVariable("characNo")Integer characNo){
        return DataResult.ok(BeanUtil.copyToList(signInService.signList(characNo), DaSignInConfVo.class));
    }

    @Operation(summary = "pc端签到")
    @SaCheckLogin
    @GetMapping("/characSign/{characNo}")
    public R<Object> characSign(@PathVariable("characNo")Long characNo){
        return DataResult.ok(signInService.pcCharacSign(characNo));
    }

    @Operation(summary = "分页")
    @SaCheckPermission("signIn")
    @PostMapping("/page")
    public R<List<DaSignInConf>> signInPage(@RequestBody DaSignInConfQo daSignInConfQo){
        return DataResult.ok(signInService.signInPage(daSignInConfQo));
    }

    @Operation(summary = "详情")
    @SaCheckPermission("signIn")
    @GetMapping("/info/{id}")
    public R<DaSignInConfVo> signInInfo(@PathVariable("id")Long id){
        return DataResult.ok(BeanUtil.copyProperties(signInService.info(id), DaSignInConfVo.class));
    }

    @Operation(summary = "添加")
    @SaCheckPermission("signIn.save")
    @PostMapping("/save")
    public R<Boolean> saveSignIn(@RequestBody DaSignInConfDto daSignInConf){
        return DataResult.ok(signInService.insert(daSignInConf));
    }

    @Operation(summary = "更新")
    @SaCheckPermission("signIn.update")
    @PostMapping("/update")
    public R<Boolean> updateSignIn(@RequestBody DaSignInConfDto daSignInConf){
        return DataResult.ok(signInService.update(daSignInConf));
    }

}
