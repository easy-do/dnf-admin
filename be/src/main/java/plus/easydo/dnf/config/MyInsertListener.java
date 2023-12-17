package plus.easydo.dnf.config;

import com.mybatisflex.annotation.InsertListener;

/**
 * @author laoyu
 * @version 1.0
 * @description 监听 entity 的 insert 行为
 * @date 2023/10/16
 */

public class MyInsertListener implements InsertListener {

    @Override
    public void onInsert(Object entity) {
//        Class<? extends Object> clazz = entity.getClass();
//        JSONObject enJson = JSONUtil.parseObj(entity);
//        String newJsonStr = CharsetUtil.convert(enJson.toStringPretty(), "utf8", "latin1");
//        entity = JSONUtil.toBean(newJsonStr,clazz);
    }
}
