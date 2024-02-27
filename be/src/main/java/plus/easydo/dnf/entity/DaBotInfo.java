package plus.easydo.dnf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 机器人信息 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "da_bot_info")
public class DaBotInfo {

    /**
     * 自增ID
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 机器人编码
     */
    @Column(value = "bot_number")
    private String botNumber;

    /**
     * 机器人秘钥
     */
    @Column(value = "bot_secret")
    private String botSecret;

    /**
     * 备注
     */
    @Column(value = "remark")
    private String remark;

    /**
     * 机器人通讯地址
     */
    @Column(value = "bot_url")
    private String botUrl;


    /**
     * 最后心跳时间
     */
    @Column(value = "last_heartbeat_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastHeartbeatTime;

    /**
     * 拓展数据
     */
    @Column(value = "ext_data")
    private String extData;

}
