package plus.easydo.dnf.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.dnf.constant.SystemConstant;
import plus.easydo.dnf.entity.DaChannel;
import plus.easydo.dnf.entity.DaGameConfig;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.manager.CacheManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author laoyu
 * @version 1.0
 * @description 简易docker工具类
 * @date 2023/12/2
 */
@Slf4j
public class DockerUtil {

    private DockerUtil() {
    }


    public static String getNetwork() {
        DaGameConfig config1 = CacheManager.GAME_CONF_MAP.get(SystemConstant.DOCKER_INSPECT_CMD);
        String res1 = RuntimeUtil.execForStr(CharSequenceUtil.format(config1.getConfData(), "dnfserver"));
        JSONArray jsonArray = JSONUtil.parseArray(res1);
        JSONObject json = JSONUtil.parseObj(jsonArray.get(0));
        String network = (String) json.getByPath("HostConfig.NetworkMode");
        log.info("服务端网络:{}", network);
        return network;
    }

    /**
     * 获取服务端ip
     *
     * @return java.lang.String
     * @author laoyu
     * @date 2023/12/2
     */
    public static String getServerIp() {
        DaGameConfig config = CacheManager.GAME_CONF_MAP.get(SystemConstant.GET_SERVER_IP_CMD);
        List<String> res = RuntimeUtil.execForLines(config.getConfData());
        if (res.size() >= 2) {
            String line = res.get(1);
            line = CharSequenceUtil.subAfter(line, "(", false);
            line = CharSequenceUtil.subBefore(line, ")", false);
            log.info("服务端ip:{}", line);
            return line;
        }
        throw new BaseException("获取服务端ip失败:{}" + JSONUtil.toJsonStr(res));
    }

    /**
     * 获取后台ip
     *
     * @return java.lang.String
     * @author laoyu
     * @date 2023/12/2
     */
    public static String getDaIp() {
        DaGameConfig config = CacheManager.GAME_CONF_MAP.get(SystemConstant.GET_DA_IP_CMD);
        List<String> res = RuntimeUtil.execForLines(config.getConfData());
        if (res.size() >= 2) {
            String line = res.get(1);
            line = CharSequenceUtil.subAfter(line, "(", false);
            line = CharSequenceUtil.subBefore(line, ")", false);
            log.info("后台ip:{}", line);
            return line;
        }
        throw new BaseException("获取后台ip失败:{}" + JSONUtil.toJsonStr(res));
    }

    /**
     * 获取服务端主线程
     *
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author laoyu
     * @date 2023/12/2
     */
    public static Map<String, String> getGamePid() {
        DaGameConfig config = CacheManager.GAME_CONF_MAP.get(SystemConstant.GET_PID_PS_CMD);
        List<String> res = RuntimeUtil.execForLines(config.getConfData());
        Map<String, String> result = new HashMap<>();
        res.forEach(line -> {
            if (line.contains("./df_game_r")) {
                List<String> b = CharSequenceUtil.split(line, " ", true, true);
                result.put(b.get(b.size() - 2), b.get(1));
            }
        });
        if(result.isEmpty()){
            log.info("获取频道进程信息为空,{}", result);
        }
        return result;
    }

    public static List<String> getFridaClientLogs(String channel) {
        DaGameConfig config = CacheManager.GAME_CONF_MAP.get(SystemConstant.DOCKER_LOG_CMD);
        return RuntimeUtil.execForLines(CharSequenceUtil.format(config.getConfData(), "frida-client-" + channel));
    }

    /**
     * 获取所frida客户端id
     *
     * @param config config
     * @return java.util.List<java.lang.String>
     * @author laoyu
     * @date 2023/12/3
     */
    private static List<String> getFridaClientIds(DaGameConfig config) {
        List<String> res = RuntimeUtil.execForLines(config.getConfData());
        List<String> result = new ArrayList<>();
        res.forEach(line -> {
            List<String> a = CharSequenceUtil.split(line, " ", true, true);
            String name = a.get(a.size() - 1);
            if (name.startsWith("frida-client-")) {
                result.add(name);
            }
        });
        if(result.isEmpty()){
            log.info("获取frida容器信息为空:{}", result);
        }
        return result;
    }

    /**
     * 获取所有正在运行的frida客户端id
     *
     * @return java.util.List<java.lang.String>
     * @author laoyu
     * @date 2023/12/3
     */
    public static List<String> getFridaClientRunIds() {
        DaGameConfig config = CacheManager.GAME_CONF_MAP.get(SystemConstant.GET_CONTAINER_LIST_RUN_CMD);
        return getFridaClientIds(config);
    }

    /**
     * 获取所有frida客户端id
     *
     * @param
     * @return java.util.List<java.lang.String>
     * @author laoyu
     * @date 2023/12/3
     */
    public static List<String> getFridaClientAllIds() {
        DaGameConfig config = CacheManager.GAME_CONF_MAP.get(SystemConstant.GET_CONTAINER_LIST_ALL_CMD);
        return getFridaClientIds(config);
    }

    /**
     * 停止所有frida客户端
     *
     * @author laoyu
     * @date 2023/12/2
     */
    public static void stopAllFridaClient() {
        List<String> ids = getFridaClientAllIds();
//        DaGameConfig stopConfig = CacheManager.GAME_CONF_MAP.get(SystemConstant.STOP_CONTAINER_CMD);
        DaGameConfig removeConfig = CacheManager.GAME_CONF_MAP.get(SystemConstant.REMOVE_CONTAINER_CMD);
        ids.forEach(id -> {
//            RuntimeUtil.execForStr(CharSequenceUtil.format(stopConfig.getConfData(), id));
            RuntimeUtil.execForStr(CharSequenceUtil.format(removeConfig.getConfData(), id));
        });
    }

    /**
     * 停止frida客户端
     *
     * @author laoyu
     * @date 2023/12/2
     */
    public static List<String> stopFridaClient(String name) {
//        DaGameConfig stopConfig = CacheManager.GAME_CONF_MAP.get(SystemConstant.STOP_CONTAINER_CMD);
        DaGameConfig removeConfig = CacheManager.GAME_CONF_MAP.get(SystemConstant.REMOVE_CONTAINER_CMD);
//        RuntimeUtil.execForLines(CharSequenceUtil.format(stopConfig.getConfData(), name));
        return RuntimeUtil.execForLines(CharSequenceUtil.format(removeConfig.getConfData(), name));
    }


    /**
     * 重启频道的frida客户端
     *
     * @param channel channel
     * @return string
     * @author laoyu
     * @date 2023/12/2
     */
    public static List<String> startChannelFrida(DaChannel channel, boolean scriptIsBlank, boolean mainIsBlank) {
        DaGameConfig jsPathConfig = CacheManager.GAME_CONF_MAP.get(SystemConstant.FRIDA_JS_PATH);
        DaGameConfig mainPathConfig = CacheManager.GAME_CONF_MAP.get(SystemConstant.MAIN_PYTHON_PATH);
        DaGameConfig jsonPathConfig = CacheManager.GAME_CONF_MAP.get(SystemConstant.FRIDA_JSON_PATH);
        // /data/frida/{}/frida.js
        String jsPath = CharSequenceUtil.format(jsPathConfig.getConfData(), channel.getChannelName());
        String mainPath = CharSequenceUtil.format(mainPathConfig.getConfData(), channel.getChannelName());
        if (!scriptIsBlank) {
            FileUtil.writeUtf8String(channel.getScriptContext(), jsPath);
        }
        if (!mainIsBlank) {
            FileUtil.writeUtf8String(channel.getMainPython(), mainPath);
        }
        //处理json
        String jsonContext = channel.getFridaJsonContext();
        if (CharSequenceUtil.isNotBlank(jsonContext)) {
            jsonContext = CacheManager.GAME_CONF_MAP.get(SystemConstant.FRIDA_JSON_DEFAULT_VALUE).getConfData();
        }
        JSONObject fridaJson = JSONUtil.parseObj(jsonContext);
        JSONObject gameConfJson = JSONUtil.createObj();
        CacheManager.GAME_CONF_MAP.forEach((s, daGameConfig) -> {
            if (!daGameConfig.getIsSystemConf()) {
                gameConfJson.set(s, daGameConfig.getConfData());
            }
        });
        fridaJson.set("game_config", gameConfJson);
        FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(fridaJson), CharSequenceUtil.format(jsonPathConfig.getConfData(), channel.getChannelName()));
        DaGameConfig startConfig = CacheManager.GAME_CONF_MAP.get(SystemConstant.START_FRIDA_CLIENT_CMD);
        String serverIp = getServerIp();
        String daIp = getDaIp();
        DaGameConfig wssConfig = CacheManager.GAME_CONF_MAP.get(SystemConstant.DA_WSS_ADDRESS);
        String wssAddr = CharSequenceUtil.format(wssConfig.getConfData(), daIp);
        String network = getNetwork();
        String channelName = channel.getChannelName();
        String fridaName = "frida-client-" + channel.getChannelName();
        String secret = UUID.fastUUID().toString(true);
        WebSocketUtil.saveChannelSecret(channelName, secret);
        String cmd = CharSequenceUtil.format(startConfig.getConfData(), network, channelName, channel.getPid(), serverIp, wssAddr, secret, channelName, fridaName);
        log.info("启动frida容器 cmd:{}", cmd);
        String execRes = RuntimeUtil.execForStr(cmd);
        log.info("启动frida容器 result:{}", execRes);
        List<String> result = new ArrayList<>();
        result.add(fridaName);
        result.add(execRes);
        if (scriptIsBlank) {
            channel.setScriptContext(FileUtil.readUtf8String(jsPath));
        }
        if (mainIsBlank) {
            channel.setMainPython(FileUtil.readUtf8String(mainPath));
        }
        return result;
    }

    public static void flushConf(List<DaChannel> channels) {
        DaGameConfig jsonPathConfig = CacheManager.GAME_CONF_MAP.get(SystemConstant.FRIDA_JSON_PATH);
        JSONObject gameConfJson = JSONUtil.createObj();
        CacheManager.GAME_CONF_MAP.forEach((s, daGameConfig) -> {
            if (!daGameConfig.getIsSystemConf()) {
                gameConfJson.set(s, daGameConfig.getConfData());
            }
        });
        channels.forEach(channel -> {
            JSONObject fridaJson = JSONUtil.parseObj(channel.getFridaJsonContext());
            fridaJson.set("game_config", gameConfJson);
            FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(fridaJson), CharSequenceUtil.format(jsonPathConfig.getConfData(), channel.getChannelName()));
        });
    }
}
