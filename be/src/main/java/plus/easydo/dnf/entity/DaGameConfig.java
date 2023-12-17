package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
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
@Table(value = "da_game_config")
public class DaGameConfig {

    /**
     * id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 配置名称
     */
    @Column(value = "conf_name")
    private String confName;

    /**
     * 配置类型
     */
    @Column(value = "conf_type")
    private Integer confType;

    /**
     * 配置信息
     */
    @Column(value = "conf_data")
    private String confData;

    /**
     * 配置标签
     */
    @Column(value = "conf_key")
    private String confKey;

    /**
     * 备注
     */
    @Column(value = "remark")
    private String remark;

    /**
     * 是否系统配置
     */
    @Column(value = "is_system_conf")
    private Boolean isSystemConf;

}
