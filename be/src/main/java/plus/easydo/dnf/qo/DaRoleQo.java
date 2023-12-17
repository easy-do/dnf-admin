package plus.easydo.dnf.qo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色信息表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class DaRoleQo extends PageQo{

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 显示顺序
     */
    private Integer roleSort;

    /**
     * 是否默认角色,0否 1是
     */
    private Boolean isDefault;

    /**
     * 角色状态（0正常 1停用）
     */
    private Boolean status;

    /**
     * 备注
     */
    private String remark;

}
