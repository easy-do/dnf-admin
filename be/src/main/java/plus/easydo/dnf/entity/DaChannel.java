package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import plus.easydo.dnf.config.Base64OnSetListener;
import plus.easydo.dnf.config.ChannelBase64InsertListener;
import plus.easydo.dnf.config.ChannelBase64UpdateListener;


/**
 * 频道信息 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Table(value = "da_channel", onSet = Base64OnSetListener.class , onInsert = ChannelBase64InsertListener.class, onUpdate = ChannelBase64UpdateListener.class)
public class DaChannel {

    /**
     * 编号
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * PID
     */
    @Column(value = "pid")
    private String pid;

    /**
     * 频道名
     */
    @Column(value = "channel_name")
    private String channelName;

    /**
     * 客户端
     */
    @Column(value = "frida_client")
    private String fridaClient;

    /**
     * 脚本内容
     */
    @Column(value = "script_context")
    private String scriptContext;

    /**
     * python脚本
     */
    @Column(value = "main_python")
    private String mainPython;

    /**
     * 状态 0离线 1在线
     */
    @Column(value = "channel_status")
    private Boolean channelStatus;

    /**
     * 状态 0离线 1运行
     */
    @Column(value = "frida_status")
    private Boolean fridaStatus;

    /**
     * frida的json配置信息
     */
    @Column(value = "frida_json_context")
    private String fridaJsonContext;

}
