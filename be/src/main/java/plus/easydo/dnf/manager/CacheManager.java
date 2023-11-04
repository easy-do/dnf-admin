package plus.easydo.dnf.manager;


import cn.hutool.core.date.LocalDateTimeUtil;
import plus.easydo.dnf.entity.DaGameConfigEntity;
import plus.easydo.dnf.vo.CallResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author laoyu
 * @version 1.0
 * @description 系统管理
 * @date 2023/10/12
 */

public class CacheManager {

    /**
     * 游戏在线账号信息
     */
    public static final Map<Integer, String> GAME_ONLINE_USER = new ConcurrentHashMap<>();
    public static final Map<String, String> DP_PING_CACHE = new ConcurrentHashMap<>();
    public static final Map<String, Map<LocalDateTime, List<CallResult>>> EXEC_LIST_MAP = new ConcurrentHashMap<>();

    public static final List<DaGameConfigEntity> GAME_CONF_LIST = new ArrayList<>();

    private CacheManager() {
    }

    public static void initOptExecListMap(String opt) {
        Map<LocalDateTime, List<CallResult>> optMap = EXEC_LIST_MAP.get(opt);
        if (Objects.isNull(optMap)) {
            EXEC_LIST_MAP.put(opt, new ConcurrentHashMap<>());
        }
    }

    /**
     * 消费指令
     *
     * @param opt opt
     * @return java.util.List<plus.easydo.dnf.vo.CallResult>
     * @author laoyu
     * @date 2023/11/4
     */
    public static List<CallResult> consumeExecList(String opt) {
        Map<LocalDateTime, List<CallResult>> optMap = EXEC_LIST_MAP.get(opt);
        if (Objects.nonNull(optMap)) {
            Iterator<Map.Entry<LocalDateTime, List<CallResult>>> iterator = optMap.entrySet().iterator();
            if (iterator.hasNext()) {
                Map.Entry<LocalDateTime, List<CallResult>> entry = iterator.next();
                LocalDateTime key = entry.getKey();
                List<CallResult> value = entry.getValue();
                optMap.remove(key);
                return value;
            }
        }
        return Collections.emptyList();
    }

    /**
     * 指定频道发送指令
     *
     * @param opt         opt
     * @param callResults execList
     * @author laoyu
     * @date 2023/11/4
     */
    public static void addExecList(String opt, CallResult... callResults) {
        Map<LocalDateTime, List<CallResult>> optMap = EXEC_LIST_MAP.get(opt);
        if (Objects.nonNull(optMap)) {
            optMap.put(LocalDateTimeUtil.now(), List.of(callResults));
        }else {
            addAllOptExecList(callResults);
        }
    }

    /**
     * 向第一个频道发送指令
     *
     * @param callResults callResults
     * @author laoyu
     * @date 2023/11/4
     */
    public static void addFirstOptExecList(CallResult... callResults) {
        Iterator<Map.Entry<String, Map<LocalDateTime, List<CallResult>>>> iterator = EXEC_LIST_MAP.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<String, Map<LocalDateTime, List<CallResult>>> firstEntry = iterator.next();
            EXEC_LIST_MAP.get(firstEntry.getKey()).put(LocalDateTimeUtil.now(), List.of(callResults));
        }
    }

    /**
     * 所有频道发送指令
     *
     * @param callResults callResults
     * @author laoyu
     * @date 2023/11/4
     */
    public static void addAllOptExecList(CallResult... callResults) {
        EXEC_LIST_MAP.forEach((opt, optMap) -> optMap.put(LocalDateTimeUtil.now(), List.of(callResults)));
    }


}
