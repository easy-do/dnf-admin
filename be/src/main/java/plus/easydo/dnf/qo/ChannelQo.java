package plus.easydo.dnf.qo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


/**
 * 频道信息 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ChannelQo extends PageQo{

    /**
     * PID
     */
    private String pid;

    /**
     * 频道名
     */
    private String channelName;


    /**
     * 状态 0离线 1在线
     */
    private Boolean channelStatus;

    /**
     * 状态 0离线 1运行
     */
    private Boolean fridaStatus;

}
