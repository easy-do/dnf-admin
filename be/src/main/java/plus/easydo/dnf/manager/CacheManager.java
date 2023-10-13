package plus.easydo.dnf.manager;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.text.CharSequenceUtil;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.exception.BaseException;

import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description 系统管理
 * @date 2023/10/12
 */

public class CacheManager {

    /**账号id与token缓存*/
    public static final TimedCache<Integer, String> TOKEN_CACHE = CacheUtil.newTimedCache(1800000L);
    /**token与账号信息缓存*/
    public static final TimedCache<String, Accounts> ACCOUNTS_CACHE = CacheUtil.newTimedCache(1800000L);

    public static boolean isLogin(Integer uid){
        return TOKEN_CACHE.containsKey(uid);
    }

    public static void cleanToken(Integer uid) {
        String cacheToken = TOKEN_CACHE.get(uid);
        ACCOUNTS_CACHE.remove(cacheToken);
        TOKEN_CACHE.remove(uid);
    }

    public static void cleanToken(String token) {
        if(CharSequenceUtil.isBlank(token)){
            throw new BaseException("无效凭证或登录已过期");
        }
        Accounts accounts = ACCOUNTS_CACHE.get(token);
        if(Objects.isNull(accounts)){
            throw new BaseException("无效凭证或登录已过期");
        }
        ACCOUNTS_CACHE.remove(token);
        TOKEN_CACHE.remove(accounts.getUid());
    }

    public static void cacheUser(Accounts accounts, String token) {
        TOKEN_CACHE.put(accounts.getUid(),token);
        ACCOUNTS_CACHE.put(token,accounts);
    }

    public static Accounts getCurrentUser(String token) {
        return ACCOUNTS_CACHE.get(token);
    }
}
