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

    /**回调函数时的第一个参数*/
    private Integer arg0;

    /**回调函数时的第二个参数*/
    private Integer arg1;

    /**回调函数时的第三个参数,里面详细描述调用的函数的以及参数集合*/
    private Object arg2;

    /**是否调用frida脚本*/
    private Boolean callFrida;

    /**是否调用dp脚本*/
    private Boolean callDp;

    /**是否打印debug日志*/
    private Boolean debug;


    public static <T> DpResult build(String type, T value){
        return DpResult.builder().arg0(9999).arg1(9999).debug(false).callFrida(false).callDp(false).type(type).arg2(value).build();
    }

    public static <T> DpResult buildDebug(String type, T value){
        return DpResult.builder().arg0(9999).arg1(9999).debug(true).callFrida(false).callDp(false).type(type).arg2(value).build();
    }

    public static <T> DpResult buildCallFrida(String type, T value){
        return DpResult.builder().arg0(9999).arg1(9999).debug(false).callFrida(true).callDp(false).type(type).arg2(value).build();
    }

    public static <T> DpResult buildCallFridaDebug(String type, T value){
        return DpResult.builder().arg0(9999).arg1(9999).debug(true).callFrida(true).callDp(false).type(type).arg2(value).build();
    }

    public static <T> DpResult buildCallDp(String type, T value){
        return DpResult.builder().arg0(9999).arg1(9999).debug(false).callFrida(false).callDp(true).type(type).arg2(value).build();
    }

    public static <T> DpResult buildCallDpDebug(String type, T value){
        return DpResult.builder().arg0(9999).arg1(9999).debug(true).callFrida(false).callDp(true).type(type).arg2(value).build();
    }

    public static <T> DpResult buildCallFridaAndDp(String type, T value){
        return DpResult.builder().arg0(9999).arg1(9999).debug(false).callFrida(true).callDp(true).type(type).arg2(value).build();
    }

    public static <T> DpResult buildCallFridaAndDpDebug(String type, T value){
        return DpResult.builder().arg0(9999).arg1(9999).debug(true).callFrida(true).callDp(true).type(type).arg2(value).build();
    }

}
