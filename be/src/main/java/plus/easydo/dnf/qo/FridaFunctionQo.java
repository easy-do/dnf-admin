package plus.easydo.dnf.qo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


/**
 * frida函数信息 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class FridaFunctionQo extends PageQo{


    /**
     * 函数名
     */
    private String functionName;
    /**
     * 函数名
     */
    private String functionKey;

    /**
     * 备注
     */
    private String remark;

    /**
     * 系统函数
     */
    private Boolean isSystemFun;
}
