package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

/**
 * 用户和角色关联表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Table(value = "da_user_role")
public class DaUserRole {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;


}
