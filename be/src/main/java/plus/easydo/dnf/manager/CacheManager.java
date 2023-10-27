package plus.easydo.dnf.manager;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author laoyu
 * @version 1.0
 * @description 系统管理
 * @date 2023/10/12
 */

public class CacheManager {

    /**游戏在线账号信息*/
    public static final Map<Integer, String> GAME_ONLINE_USER = new ConcurrentHashMap<>();

    public static final List<String> DP_PING_CACHE = new ArrayList<>();
}
