package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.Builder;
import lombok.Data;

/**
 * 角色和资源关联表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@Table(value = "da_role_resource")
public class DaRoleResource {

    /**
     * 角色ID
     */
    @Column(value = "role_id")
    private Long roleId;

    /**
     * 资源ID
     */
    @Column(value = "resource_id")
    private Long resourceId;


}
