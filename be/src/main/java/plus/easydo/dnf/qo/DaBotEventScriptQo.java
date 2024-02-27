package plus.easydo.dnf.qo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 机器人脚本 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class DaBotEventScriptQo extends PageQo{


    /**
     * 脚本名称
     */
    private String scriptName;

    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 脚本类型
     */
    private String scriptType;

    /**
     * 备注
     */
    private String remark;


}
