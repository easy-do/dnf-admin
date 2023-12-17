package plus.easydo.dnf.manager;


import plus.easydo.dnf.entity.DaGameConfig;

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


    public static final List<DaGameConfig> GAME_CONF_LIST = new ArrayList<>();

    public static final Map<String,DaGameConfig> GAME_CONF_MAP = new HashMap<>();

    private CacheManager() {
    }


}
