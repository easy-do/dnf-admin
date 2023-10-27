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

    private String type;

    private Integer arg0;

    private Integer arg1;

    private Object arg2;

    private Boolean callFrida;

    private Boolean callDp;

    private Boolean debug;


    public static <T> DpResult build(String type, T value){
        return DpResult.builder().arg0(0).arg1(0).debug(false).callFrida(false).callDp(false).type(type).arg2(value).build();
    }

    public static <T> DpResult buildDebug(String type, T value){
        return DpResult.builder().arg0(0).arg1(0).debug(true).callFrida(false).callDp(false).type(type).arg2(value).build();
    }

    public static <T> DpResult buildCallFrida(String type, T value){
        return DpResult.builder().arg0(0).arg1(0).debug(false).callFrida(true).callDp(false).type(type).arg2(value).build();
    }

    public static <T> DpResult buildCallFridaDebug(String type, T value){
        return DpResult.builder().arg0(0).arg1(0).debug(true).callFrida(true).callDp(false).type(type).arg2(value).build();
    }

    public static <T> DpResult buildCallDp(String type, T value){
        return DpResult.builder().arg0(0).arg1(0).debug(false).callFrida(false).callDp(true).type(type).arg2(value).build();
    }

    public static <T> DpResult buildCallDpDebug(String type, T value){
        return DpResult.builder().arg0(0).arg1(0).debug(true).callFrida(false).callDp(true).type(type).arg2(value).build();
    }

    public static <T> DpResult buildCallFridaAndDp(String type, T value){
        return DpResult.builder().arg0(0).arg1(0).debug(false).callFrida(true).callDp(true).type(type).arg2(value).build();
    }

    public static <T> DpResult buildCallFridaAndDpDebug(String type, T value){
        return DpResult.builder().arg0(0).arg1(0).debug(true).callFrida(true).callDp(true).type(type).arg2(value).build();
    }

}
