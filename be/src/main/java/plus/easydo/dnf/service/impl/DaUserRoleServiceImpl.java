package plus.easydo.dnf.service.impl;


import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.service.IDaUserRoleService;
import plus.easydo.dnf.entity.DaUserRole;
import plus.easydo.dnf.mapper.DaUserRoleMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

import java.util.List;

import static plus.easydo.dnf.entity.table.DaUserRoleTableDef.DA_USER_ROLE;

/**
 * 用户和角色关联表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class DaUserRoleServiceImpl extends ServiceImpl<DaUserRoleMapper, DaUserRole> implements IDaUserRoleService {

    @Override
    public List<DaUserRole> userRole() {
        long userId = StpUtil.getLoginIdAsLong();
        return userRole(userId);
    }

    public List<DaUserRole> userRole(Long userId) {
        QueryWrapper queryWrapper = query().and(DA_USER_ROLE.USER_ID.in(userId));
        return list(queryWrapper);
    }

    @Override
    public List<Long> userRoleIds(Long userId) {
        return userRole(userId).stream().map(DaUserRole::getRoleId).toList();
    }

    @Override
    public List<Long> userRoleIds() {
        return userRole().stream().map(DaUserRole::getRoleId).toList();
    }
}
