package plus.easydo.dnf.manager;


import plus.easydo.dnf.entity.CharacInfo;
import plus.easydo.dnf.entity.DaBotEventScript;
import plus.easydo.dnf.entity.DaBotInfo;
import plus.easydo.dnf.entity.DaGameConfig;
import plus.easydo.dnf.entity.DaItemEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author laoyu
 * @version 1.0
 * @description 系统管理
 * @date 2023/10/12
 */

public class CacheManager {

    /** 所有系统游戏配置缓存 */
    public static final List<DaGameConfig> GAME_CONF_LIST = new ArrayList<>();
    /** 系统游戏配置Map缓存 */
    public static final Map<String,DaGameConfig> GAME_CONF_MAP = new HashMap<>();
    /** 角色缓存 */
    public static final Map<Long, CharacInfo> CHARAC_INFO_CACHE = new HashMap<>();
    /** 物品缓存 */
    public static final Map<Long, DaItemEntity> ITEM_INFO_CACHE = new HashMap<>();
    /** 机器人缓存 */
    public static final Map<String, DaBotInfo> BOT_CACHE = new HashMap<>();
    /** 机器人配置缓存 */
    public static final Map<String, Map<String,String>> BOT_CONF_CACHE = new HashMap<>();
    /** 事件与脚本对应关系 */
    public static final Map<String, List<Long>> EVENT_SCRIPT_ID_CACHE = new HashMap<>();
    /** 机器人与脚本对应关系 */
    public static final Map<String, List<Long>> BOT_SCRIPT_ID_CACHE = new HashMap<>();
    /** 所有脚本缓存 */
    public static final Map<Long, DaBotEventScript> EVENT_SCRIPT_CACHE = new HashMap<>();

    private CacheManager() {
    }


}
