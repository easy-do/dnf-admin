package plus.easydo.dnf.manager;


import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.entity.DaBotPlatformBind;
import plus.easydo.dnf.mapper.DaBotPlatformBindMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

import static plus.easydo.dnf.entity.table.DaBotPlatformBindTableDef.DA_BOT_PLATFORM_BIND;

/**
 * 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class DaBotPlatformBindManager extends ServiceImpl<DaBotPlatformBindMapper, DaBotPlatformBind> {

    public boolean isBindRole(String userId) {
        QueryWrapper queryWrapper = query().and(DA_BOT_PLATFORM_BIND.BOT_NUMBER.eq(userId)).and(DA_BOT_PLATFORM_BIND.ROLE_ID.isNotNull());
        return exists(queryWrapper);
    }

    public boolean isBindAccount(String userId) {
        QueryWrapper queryWrapper = query().and(DA_BOT_PLATFORM_BIND.BOT_NUMBER.eq(userId));
        return exists(queryWrapper);
    }

    public boolean isBindAccounts(Long uId) {
        QueryWrapper queryWrapper = query().and(DA_BOT_PLATFORM_BIND.UID.eq(uId));
        return exists(queryWrapper);
    }

    public boolean bind(String userId, Long accounts) {
        DaBotPlatformBind entityQ = DaBotPlatformBind.builder()
                .botNumber(userId)
                .uid(accounts)
                .build();
        return save(entityQ);
    }

    public DaBotPlatformBind getBindByPlatformNumber(String userId) {
        QueryWrapper queryWrapper = query().and(DA_BOT_PLATFORM_BIND.BOT_NUMBER.eq(userId));
        return getOne(queryWrapper);
    }

    public boolean updateBind(DaBotPlatformBind daBotPlatformBind) {
        QueryWrapper queryWrapper = query().and(DA_BOT_PLATFORM_BIND.BOT_NUMBER.eq(daBotPlatformBind.getBotNumber())).and(DA_BOT_PLATFORM_BIND.UID.eq(daBotPlatformBind.getUid()));
        return update(daBotPlatformBind,queryWrapper);
    }
}
