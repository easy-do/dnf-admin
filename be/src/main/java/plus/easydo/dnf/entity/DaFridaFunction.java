package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import plus.easydo.dnf.config.Base64OnSetListener;
import plus.easydo.dnf.config.FridaFunctionBase64InsertListener;
import plus.easydo.dnf.config.FridaFunctionBase64UpdateListener;


/**
 * frida函数信息 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Table(value = "da_frida_function", onSet = Base64OnSetListener.class , onInsert = FridaFunctionBase64InsertListener.class, onUpdate = FridaFunctionBase64UpdateListener.class)
public class DaFridaFunction {

    /**
     * 编号
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 依赖函数
     */
    @Column(value = "children_function")
    private String childrenFunction;

    /**
     * 函数名
     */
    @Column(value = "function_name")
    private String functionName;

    /**
     * 函数唯一标识
     */
    @Column(value = "function_Key")
    private String functionKey;

    /**
     * 函数内容
     */
    @Column(value = "function_context")
    private String functionContext;

    /**
     * 备注
     */
    @Column(value = "remark")
    private String remark;

    /**
     * 系统函数
     */
    @Column(value = "is_system_fun")
    private Boolean isSystemFun;

}
