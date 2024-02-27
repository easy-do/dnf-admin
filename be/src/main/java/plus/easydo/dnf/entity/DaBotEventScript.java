package plus.easydo.dnf.entity;

import lombok.Data;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

/**
 * 机器人脚本 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Table(value = "da_bot_event_script")
public class DaBotEventScript {

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 脚本名称
     */
    @Column(value = "script_name")
    private String scriptName;

    /**
     * 事件类型
     */
    @Column(value = "event_type")
    private String eventType;

    /**
     * 脚本类型
     */
    @Column(value = "script_type")
    private String scriptType;

    /**
     * 脚本内容
     */
    @Column(value = "script_content")
    private String scriptContent;

    /**
     * 备注
     */
    @Column(value = "remark")
    private String remark;


}
