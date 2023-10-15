package plus.easydo.dnf.controller;

import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.entity.DaSignInConf;
import plus.easydo.dnf.qo.DaSignInConfQo;
import plus.easydo.dnf.security.CurrentUserContextHolder;
import plus.easydo.dnf.service.SignInService;
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

    @GetMapping("/signInList/{roleId}")
    public R<List<DaSignInConf>> signList(@PathVariable("roleId")Integer roleId){
        return DataResult.ok(signInService.signList(roleId));
    }

    @GetMapping("/roleSign/{roleId}")
    public R<Object> roleSign(@PathVariable("roleId")Integer roleId){
        return DataResult.ok(signInService.roleSign(roleId));
    }

    @PostMapping("/page")
    public R<Page<DaSignInConf>> signInPage(@RequestBody DaSignInConfQo daSignInConfQo){
        CurrentUserContextHolder.checkAdmin();
        return DataResult.ok(signInService.signInPage(daSignInConfQo));
    }

    @GetMapping("/info/{id}")
    public R<DaSignInConf> info(@PathVariable("id")Long id){
        return DataResult.ok(signInService.info(id));
    }

    @PostMapping("/update")
    public R<Boolean> update(@RequestBody DaSignInConf daSignInConf){
        CurrentUserContextHolder.checkAdmin();
        return DataResult.ok(signInService.update(daSignInConf));
    }

}
