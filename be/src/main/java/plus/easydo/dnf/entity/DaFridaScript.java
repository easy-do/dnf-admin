package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import plus.easydo.dnf.config.Base64OnSetListener;
import plus.easydo.dnf.config.FridaScriptBase64InsertListener;
import plus.easydo.dnf.config.FridaScriptBase64UpdateListener;


/**
 * frida脚本信息 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Table(value = "da_frida_script", onSet = Base64OnSetListener.class ,onInsert = FridaScriptBase64InsertListener.class, onUpdate = FridaScriptBase64UpdateListener.class)
public class DaFridaScript {

    /**
     * 编号
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 脚本名
     */
    @Column(value = "script_name")
    private String scriptName;

    /**
     * 脚本内容
     */
    @Column(value = "script_context")
    private String scriptContext;

    /**
     * 依赖函数
     */
    @Column(value = "children_function")
    private String childrenFunction;

    /**
     * 备注
     */
    @Column(value = "remark")
    private String remark;

}
