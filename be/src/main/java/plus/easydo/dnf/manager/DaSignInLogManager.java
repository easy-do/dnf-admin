package plus.easydo.dnf.manager;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.entity.DaSignInLog;
import plus.easydo.dnf.mapper.DaSignInLogMapper;

import static plus.easydo.dnf.entity.table.DaSignInLogTableDef.DA_SIGN_IN_LOG;

/**
 * @author laoyu
 * @version 1.0
 * @description 签到日志
 * @date 2023/10/16
 */
@Component
public class DaSignInLogManager extends ServiceImpl<DaSignInLogMapper, DaSignInLog> {
    public boolean existRoleConfigLog(Integer uid, Integer roleId, Long configId) {
        return exists(QueryWrapper.create()
                .from(DA_SIGN_IN_LOG).where(DA_SIGN_IN_LOG.SIGN_IN_ROLE_ID.eq(roleId)
                        .and(DA_SIGN_IN_LOG.SIGN_IN_USER_ID.eq(uid)).and(DA_SIGN_IN_LOG.CONFIG_ID.eq(configId))));
    }
}
