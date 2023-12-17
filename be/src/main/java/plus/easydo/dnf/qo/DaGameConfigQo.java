package plus.easydo.dnf.qo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 游戏配置 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class DaGameConfigQo extends PageQo{

    /**
     * 配置名称
     */
    private String confName;

    /**
     * 配置类型
     */
    private Integer confType;

    /**
     * 配置标签
     */
    private String confKey;

}
