package plus.easydo.dnf.config;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import plus.easydo.dnf.entity.Accounts;

/**
 * @author laoyu
 * @version 1.0
 * @description 系统缓存
 * @date 2023/10/12
 */

public class SystemCache {

    public static final TimedCache<Integer, String> TOKEN_CACHE = CacheUtil.newTimedCache(1800000L);
    public static final TimedCache<String, Accounts> ACCOUNTS_CACHE = CacheUtil.newTimedCache(1800000L);
}
