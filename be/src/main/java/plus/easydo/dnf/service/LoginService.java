package plus.easydo.dnf.service;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.dto.LoginDto;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.manager.CacheManager;

import java.util.Map;
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


    /**
     * 登录
     *
     * @param loginDto loginDto
     * @return java.lang.String
     * @author laoyu
     * @date 2023/10/14
     */
    public String login(LoginDto loginDto){
        Accounts accounts = accountsService.getByUserName(loginDto.getUserName());
        if(Objects.isNull(accounts)){
            throw new BaseException("账号不存在");
        }
        String md5Password = SecureUtil.md5().digestHex(loginDto.getPassword());
        if(CharSequenceUtil.equals(md5Password,accounts.getPassword())){
            String token = generateToken(accounts);
            if(CacheManager.isLogin(accounts.getUid())){
                CacheManager.cleanToken(accounts.getUid());
            }
            CacheManager.cacheUser(accounts,token);
            return token;
        }
        throw new BaseException("密码错误");
    }

    /**
     * 退出
     *
     * @param token token
     * @return java.lang.String
     * @author laoyu
     * @date 2023/10/14
     */
    public void logout(String token) {
        CacheManager.cleanToken(token);
    }

    /**
     * 生成一个简易token
     *
     * @param accounts accounts
     * @return java.lang.String
     * @author laoyu
     * @date 2023/10/11
     */
    private String generateToken(Accounts accounts){
        Map<String,Object> map = MapUtil.newHashMap(2);
        map.put("uid",accounts.getUid());
        map.put("userName",accounts.getAccountname());
        map.put("timestamp",System.currentTimeMillis());
        return JWTUtil.createToken(map, "123456".getBytes());
    }

}
