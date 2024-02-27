package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.NoArgsConstructor;

/**
 * 机器人配置 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "da_bot_conf")
public class DaBotConf {

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
     * 平台
     */
    @Column(value = "platform")
    private String platform;

    /**
     * 配置key
     */
    @Column(value = "conf_key")
    private String confKey;

    /**
     * 配置参数
     */
    @Column(value = "conf_value")
    private String confValue;

    /**
     * 备注
     */
    @Column(value = "remark")
    private String remark;


}
