package plus.easydo.dnf.config;

import cn.dev33.satoken.stp.StpInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.service.IDaResourceService;
import plus.easydo.dnf.service.IDaRoleService;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 自定义权限验证接口扩展
 * @date 2023/10/19
 */
@Component
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private final IDaResourceService resourceService;

    private final IDaRoleService roleService;


    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return resourceService.userResource(Long.valueOf(loginId.toString()));
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return roleService.userRoleCodes(Long.valueOf(loginId.toString()));
    }
}
