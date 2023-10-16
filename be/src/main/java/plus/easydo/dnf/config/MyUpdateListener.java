package plus.easydo.dnf.config;

import com.mybatisflex.annotation.UpdateListener;

/**
 * @author laoyu
 * @version 1.0
 * @description 监听 entity 的 update 行为
 * @date 2023/10/16
 */

public class MyUpdateListener implements UpdateListener {

    @Override
    public void onUpdate(Object entity) {
//        Class<? extends Object> clazz = entity.getClass();
//        JSONObject enJson = JSONUtil.parseObj(entity);
//        String newJsonStr = CharsetUtil.convert(enJson.toStringPretty(), CharsetUtil.UTF_8, CharsetUtil.ISO_8859_1);
//        entity = JSONUtil.toBean(newJsonStr,clazz);

    }
}
