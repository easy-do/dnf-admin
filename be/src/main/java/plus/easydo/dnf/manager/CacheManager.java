package plus.easydo.dnf.manager;


import cn.hutool.core.date.LocalDateTimeUtil;
import plus.easydo.dnf.vo.CallResult;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
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

    private CacheManager() {
    }

    /**游戏在线账号信息*/
    public static final Map<Integer, String> GAME_ONLINE_USER = new ConcurrentHashMap<>();

    public static final List<String> DP_PING_CACHE = new LinkedList<>();

    public static final Map<LocalDateTime,List<CallResult>> EXEC_LIST_MAP = new ConcurrentHashMap<>();

    public static List<CallResult> consumeExecList(){
        Iterator<Map.Entry<LocalDateTime, List<CallResult>>> iterator = EXEC_LIST_MAP.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<LocalDateTime, List<CallResult>> entry = iterator.next();
            LocalDateTime key = entry.getKey();
            List<CallResult> value = entry.getValue();
            EXEC_LIST_MAP.remove(key);
            return value;
        } else {
            return Collections.emptyList();
        }
    }
    public static void addExecList(CallResult execList){
        EXEC_LIST_MAP.put(LocalDateTimeUtil.now(), Collections.singletonList(execList));
    }
    public static void addExecList(List<CallResult> execList){
        EXEC_LIST_MAP.put(LocalDateTimeUtil.now(),execList);
    }

    public static void remove(LocalDateTime key){
        EXEC_LIST_MAP.remove(key);
    }



}
