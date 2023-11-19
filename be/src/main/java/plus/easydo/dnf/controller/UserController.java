package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.lang.tree.Tree;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.dto.LoginDto;
import plus.easydo.dnf.service.IDaResourceService;
import plus.easydo.dnf.service.LoginService;
import plus.easydo.dnf.vo.CurrentUser;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 用户相关
 * @date 2023/10/11
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final LoginService loginService;

    private final IDaResourceService resourceService;

    @SaIgnore
    @PostMapping("/login")
    public R<String> login(@Validated @RequestBody LoginDto loginDto){
        return DataResult.ok(loginService.login(loginDto));
    }

    @GetMapping("/logout")
    public R<String> logout(){
        loginService.logout();
        return DataResult.ok();
    }

    @SaCheckLogin
    @GetMapping("/currentUser")
    public R<CurrentUser> currentUser(){
        return DataResult.ok(loginService.currentUser());
    }

    @SaCheckLogin
    @GetMapping("/userResource")
    public R<List<Tree<Long>>> userResource(){
        return DataResult.ok(resourceService.userResource());
    }

}

