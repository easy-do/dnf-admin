package plus.easydo.dnf.constant;

/**
 * @author laoyu
 * @version 1.0
 * @description 上报类型枚举
 * @date 2023/10/27
 */

public enum ReportTypeEnum {

    GET_CONF(1,ReportTypeConstant.GET_CONF,"获取配置"),
    LOGIN(2,ReportTypeConstant.LOGIN,"登录"),
    LOGOUT(3,ReportTypeConstant.LOGOUT,"退出");

    ReportTypeEnum(Integer code, String value, String desc) {
        this.code = code;
        this.value = value;
        this.desc = desc;
    }

    private final Integer code;

    private final String value;

    private final String desc;

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
