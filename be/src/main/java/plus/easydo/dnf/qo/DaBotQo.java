package plus.easydo.dnf.qo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 机器人信息 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class DaBotQo extends PageQo{


    /**
     * 机器人编码
     */
    private String botNumber;


    /**
     * 备注
     */
    private String remark;

    /**
     * 机器人类型
     */
    private String botType;

    /**
     * 机器人通讯地址
     */
    private String botUrl;


}
