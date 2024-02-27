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
 * 游戏事件 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "da_game_event")
public class DaGameEvent {

    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 账户id
     */
    @Column(value = "account_id")
    private Long accountId;

    /**
     * 日志坐标
     */
    @Column(value = "file_index")
    private Integer fileIndex;

    /**
     * 日志名称
     */
    @Column(value = "file_name")
    private String fileName;

    /**
     * 频道
     */
    @Column(value = "channel")
    private String channel;

    /**
     * 角色id
     */
    @Column(value = "charca_no")
    private Long charcaNo;

    /**
     * 角色名
     */
    @Column(value = "charca_name")
    private String charcaName;

    /**
     * 角色等级
     */
    @Column(value = "level")
    private Integer level;

    /**
     * 事件类型
     */
    @Column(value = "option_type")
    private String optionType;

    /**
     * 参数1
     */
    @Column(value = "param1")
    private String param1;

    /**
     * 参数2
     */
    @Column(value = "param2")
    private String param2;

    /**
     * 参数3
     */
    @Column(value = "param3")
    private String param3;

    /**
     * 客户端ip
     */
    @Column(value = "client_ip")
    private String clientIp;

    /**
     * 发生时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(value = "option_time")
    private LocalDateTime optionTime;


    @Column(ignore = true)
    private String optionInfo;

}
