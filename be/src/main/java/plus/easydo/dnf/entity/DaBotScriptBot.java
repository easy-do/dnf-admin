package plus.easydo.dnf.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.NoArgsConstructor;

/**
 * 机器人与脚本关联表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "da_bot_script_bot")
public class DaBotScriptBot {

    /**
     * 脚本id
     */
    @Column(value = "script_id")
    private Long scriptId;

    /**
     * 机器人编码
     */
    @Column(value = "bot_number")
    private String botNumber;


}
