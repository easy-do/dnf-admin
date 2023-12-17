package plus.easydo.dnf.service;


import com.mybatisflex.core.service.IService;
import plus.easydo.dnf.entity.DaRoleResource;

import java.util.List;

/**
 * 角色和资源关联表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IDaRoleResourceService extends IService<DaRoleResource> {

    void removeByRoleId(Long roleId);

    List<DaRoleResource> listByRoleIds(List<Long> roleIds);
}
