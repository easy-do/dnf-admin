package plus.easydo.dnf.onebot.handler;

import cn.hutool.json.JSONObject;

/**
 * @author laoyu
 * @version 1.0
 * @description OneBot上报处理
 * @date 2024/2/25
 */

public interface OneBotPostHandler {

    void handlerPost(JSONObject postData);
}
