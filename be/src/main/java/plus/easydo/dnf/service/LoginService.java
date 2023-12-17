package plus.easydo.dnf.service;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.config.SystemConfig;
import plus.easydo.dnf.dto.LoginDto;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.vo.CurrentUser;

import java.util.List;
import java.util.Objects;


/**
 * @author laoyu
 * @version 1.0
 * @description 登录工具类
 * @date 2023/10/11
 */
@Component
@RequiredArgsConstructor
public class LoginService {

    private final AccountsService accountsService;

    private final IDaRoleService roleService;

    private final IDaResourceService resourceService;

    private final SystemConfig systemConfig;


    /**
     * 登录
     *
     * @param loginDto loginDto
     * @return java.lang.String
     * @author laoyu
     * @date 2023/10/14
     */
    public String login(LoginDto loginDto) {
        Accounts accounts = accountsService.getByUserName(loginDto.getUserName());
        if (Objects.isNull(accounts)) {
            throw new BaseException("账号不存在或密码错误");
        }
        String md5Password = SecureUtil.md5().digestHex(loginDto.getPassword());
        if (CharSequenceUtil.equals(md5Password, accounts.getPassword())) {
            boolean userNameIsAdmin = loginDto.getUserName().equals(systemConfig.getAdminUser());
            List<String> roles = roleService.userRoleCodes(accounts.getUid());
            if(roles.isEmpty()){
                roleService.bindingDefaultRole(accounts.getUid());
            }
            boolean roleInAdmin = roles.contains(systemConfig.getAdminRole());
            if(userNameIsAdmin && !roleInAdmin){
                roleService.bindingUserRole(accounts.getUid(),systemConfig.getAdminRole());
            }
            boolean isAdmin =  userNameIsAdmin || roleInAdmin;
            accounts.setAdmin(isAdmin);
            StpUtil.login(accounts.getUid(), SaLoginConfig
                    .setExtra("userInfo", JSONUtil.toJsonPrettyStr(accounts)).setExtra("admin", isAdmin));
            return StpUtil.getTokenValue();
        }
        throw new BaseException("账号不存在或密码错误");
    }

    /**
     * 退出
     *
     * @return java.lang.String
     * @author laoyu
     * @date 2023/10/14
     */
    public void logout() {
        StpUtil.logout();
    }


    public CurrentUser currentUser() {
        Object userInfo = StpUtil.getExtra("userInfo");
        JSONObject userJson = JSONUtil.parseObj(userInfo);
        userJson.remove("password");
        userJson.set("menu",resourceService.userResource());
        userJson.set("role",roleService.userRoleCodes());
        userJson.set("resource",resourceService.userResourceCodes());
        return JSONUtil.toBean(userJson, CurrentUser.class);
    }


}
