package plus.easydo.dnf.controller;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.dto.LoginDto;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.security.CurrentUserContextHolder;
import plus.easydo.dnf.service.LoginService;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.util.Objects;

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

    @PostMapping("/login")
    public R<String> login(@Validated @RequestBody LoginDto loginDto){
        return DataResult.ok(loginService.login(loginDto));
    }

    @GetMapping("/logout")
    public R<String> logout(@RequestHeader("Authorization")String token){
        loginService.logout(token);
        return DataResult.ok();
    }

    @GetMapping("/currentUser")
    public R<Accounts> currentUser(){
        return DataResult.ok(CurrentUserContextHolder.getCurrentUser());
    }

}

