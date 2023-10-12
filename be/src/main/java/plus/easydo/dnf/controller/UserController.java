package plus.easydo.dnf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.dto.LoginDto;
import plus.easydo.dnf.service.LoginService;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.LoginResultVo;
import plus.easydo.dnf.vo.R;

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
    public R<LoginResultVo> login(@RequestBody LoginDto loginDto){
        return DataResult.ok(loginService.login(loginDto));
    }

}

