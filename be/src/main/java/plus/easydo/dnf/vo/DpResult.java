package plus.easydo.dnf.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description 向dp插件返回的消息封装
 * @date 2023/10/26
 */
@Data
@Builder
public class DpResult {

    /**返回的消息类型*/
    private String type;

    /**参数*/
    private Object value;

    /**是否打印debug日志*/
    private Boolean debug;


    public static <T> DpResult build(String type, T value){
        return DpResult.builder().type(type).value(value).build();
    }

}
