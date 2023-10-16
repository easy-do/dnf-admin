package plus.easydo.dnf.config;

import com.mybatisflex.annotation.SetListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author laoyu
 * @version 1.0
 * @description 监听 entity 的查询数据的 set 行为，用户主动 set 不会触发
 * @date 2023/10/16
 */
@Slf4j
public class MySetListener implements SetListener {
    @Override
    public Object onSet(Object entity, String property, Object value) {
//        if(Objects.nonNull(value) && value instanceof String){
//            log.info("SetListener,property:{},value:{}",property,value);
//            return CharsetUtil.convert( value.toString(), CharsetUtil.ISO_8859_1, CharsetUtil.UTF_8);
//        }
        return value;
    }
}
