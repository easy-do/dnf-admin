package plus.easydo.dnf.qo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 游戏事件 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class DaGameEventQo extends PageQo{

    /**
     * 账户id
     */
    private Long accountId;

    /**
     * 日志坐标
     */
    private Integer fileIndex;

    /**
     * 频道
     */
    private String channel;

    /**
     * 角色id
     */
    private Long charcaNo;

    /**
     * 角色名
     */
    private String charcaName;

    /**
     * 角色等级
     */
    private Integer level;

    /**
     * 事件类型
     */
    private String optionType;

    /**
     * 客户端ip
     */
    private String clientIp;

}
