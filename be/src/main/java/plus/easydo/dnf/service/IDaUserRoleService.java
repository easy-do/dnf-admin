package plus.easydo.dnf.service;


import plus.easydo.dnf.entity.DaUserRole;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 用户和角色关联表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IDaUserRoleService extends IService<DaUserRole> {

    List<DaUserRole> userRole();

    List<Long> userRoleIds(Long userId);

    List<Long> userRoleIds();
}
