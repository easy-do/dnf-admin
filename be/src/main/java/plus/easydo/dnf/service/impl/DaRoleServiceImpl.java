package plus.easydo.dnf.service.impl;


import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.entity.DaRole;
import plus.easydo.dnf.entity.DaUserRole;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.mapper.DaRoleMapper;
import plus.easydo.dnf.qo.DaRoleQo;
import plus.easydo.dnf.service.IDaRoleService;
import plus.easydo.dnf.service.IDaUserRoleService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static plus.easydo.dnf.entity.table.DaRoleTableDef.DA_ROLE;

/**
 * 角色信息表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class DaRoleServiceImpl extends ServiceImpl<DaRoleMapper, DaRole> implements IDaRoleService {

    private final IDaUserRoleService userRoleService;

    @Override
    public List<DaRole> userRole() {
        long userId = StpUtil.getLoginIdAsLong();
        return userRole(userId);
    }

    public List<DaRole> userRole(Long userId) {
        List<Long> roleIds = userRoleService.userRoleIds(userId);
        if(roleIds.isEmpty()){
            return Collections.emptyList();
        }
        QueryWrapper query = query().and(DA_ROLE.ID.in(roleIds));
        return list(query);
    }

    @Override
    public List<String> userRoleCodes() {
        long userId = StpUtil.getLoginIdAsLong();
        return userRoleCodes(userId);
    }
    @Override
    public List<String> userRoleCodes(Long userId) {
        List<DaRole> roles = userRole(userId);
        return roles.stream().map(DaRole::getRoleKey).toList();
    }

    DaRole getByRoleKey(String roleKey){
        QueryWrapper queryWrapper = query().and(DA_ROLE.ROLE_KEY.eq(roleKey));
        return getOne(queryWrapper);
    }

    @Override
    public void bindingUserRole(Long userId, String roleKey) {
        DaRole role = getByRoleKey(roleKey);
        if(Objects.nonNull(role)){
            DaUserRole entity = new DaUserRole();
            entity.setUserId(userId);
            entity.setRoleId(role.getId());
            userRoleService.save(entity);
        }

    }

    @Override
    public Page<DaRole> pageRole(DaRoleQo daRoleQo) {
        QueryWrapper query = query().and(DA_ROLE.ROLE_NAME.like(daRoleQo.getRoleName()))
                .and(DA_ROLE.ROLE_KEY.like(daRoleQo.getRoleKey()))
                .and(DA_ROLE.IS_DEFAULT.eq(daRoleQo.getIsDefault()))
                .and(DA_ROLE.REMARK.like(daRoleQo.getRemark()));
        return page(new Page<>(daRoleQo.getCurrent(),daRoleQo.getPageSize()),query);
    }

    @Override
    public void bindingDefaultRole(Long uid) {
        QueryWrapper query = query().and(DA_ROLE.IS_DEFAULT.eq(true));
        DaRole defaultRole = getOne(query);
        if(Objects.nonNull(defaultRole)){
            DaUserRole entity = new DaUserRole();
            entity.setUserId(uid);
            entity.setRoleId(defaultRole.getId());
            userRoleService.save(entity);
        }else {
            throw new BaseException("没有找到默认角色");
        }
    }
}
