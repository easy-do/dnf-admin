package plus.easydo.dnf.qo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


/**
 * frida脚本信息 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class FridaScriptQo extends PageQo{


    /**
     * 脚本名
     */
    private String scriptName;


    /**
     * 备注
     */
    private String remark;

}
