package plus.easydo.dnf.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 详细调用函数参数封装
 * @date 2023/10/28
 */
@Data
@Builder
public class CallResult {

    /**调用的函数名*/
    private String funName;

    /**传递的参数集合,不限制个数*/
    private List<Object> args;

    /**是否调用frida脚本*/
    private Boolean callFrida;

    /**是否调用dp脚本*/
    private Boolean callDp;

    /**是否打印debug日志*/
    private Boolean debug;

}
