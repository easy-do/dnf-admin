package plus.easydo.dnf.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 自定义权限验证接口扩展
 * @date 2023/10/19
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return Collections.emptyList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        boolean isAdmin = (boolean) StpUtil.getExtra("admin");
        if(isAdmin){
            return Collections.singletonList("admin");
        }
        return Collections.emptyList();
    }
}
