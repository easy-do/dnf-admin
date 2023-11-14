package plus.easydo.dnf.service.impl;


import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.service.IDaRoleResourceService;
import plus.easydo.dnf.entity.DaRoleResource;
import plus.easydo.dnf.mapper.DaRoleResourceMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

import java.util.List;

import static plus.easydo.dnf.entity.table.DaRoleResourceTableDef.DA_ROLE_RESOURCE;

/**
 * 角色和资源关联表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class DaRoleResourceServiceImpl extends ServiceImpl<DaRoleResourceMapper, DaRoleResource> implements IDaRoleResourceService {

    @Override
    public void removeByRoleId(Long roleId) {
        QueryWrapper queryWrapper = query().and(DA_ROLE_RESOURCE.ROLE_ID.eq(roleId));
        remove(queryWrapper);
    }

    @Override
    public List<DaRoleResource> listByRoleIds(List<Long> roleIds) {
        QueryWrapper query = query().and(DA_ROLE_RESOURCE.ROLE_ID.in(roleIds));
        return list(query);
    }
}
