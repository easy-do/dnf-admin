package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

/**
 * 角色信息表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Table(value = "da_role")
public class DaRole {

    /**
     * 角色ID
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 角色名称
     */
    @Column(value = "role_name")
    private String roleName;

    /**
     * 角色权限字符串
     */
    @Column(value = "role_key")
    private String roleKey;

    /**
     * 显示顺序
     */
    @Column(value = "role_sort")
    private Integer roleSort;

    /**
     * 是否默认角色,0否 1是
     */
    @Column(value = "is_default")
    private Boolean isDefault;

    /**
     * 角色状态（0正常 1停用）
     */
    @Column(value = "status")
    private Boolean status;

    /**
     * 备注
     */
    @Column(value = "remark")
    private String remark;

}
