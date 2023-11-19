package plus.easydo.dnf.service;


import com.mybatisflex.core.paginate.Page;
import plus.easydo.dnf.entity.DaRole;
import com.mybatisflex.core.service.IService;
import plus.easydo.dnf.qo.DaRoleQo;

import java.util.List;

/**
 * 角色信息表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IDaRoleService extends IService<DaRole> {

    List<DaRole> userRole();

    List<String> userRoleCodes(Long userId);
    List<String> userRoleCodes();

    void bindUserRole(Long userId, String roleName);

    Page<DaRole> pageRole(DaRoleQo daRoleQo);
}
