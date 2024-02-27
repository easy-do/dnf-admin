package plus.easydo.dnf.manager;


import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.entity.DaBotInfo;
import plus.easydo.dnf.mapper.DaBotInfoMapper;

import static plus.easydo.dnf.entity.table.DaBotInfoTableDef.DA_BOT_INFO;


/**
 * 机器人信息 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class DaBotInfoManager extends ServiceImpl<DaBotInfoMapper, DaBotInfo> {

    public boolean updateBybotNumber(DaBotInfo daBotInfo) {
        QueryWrapper queryWrapper = query().and(DA_BOT_INFO.BOT_NUMBER.eq(daBotInfo.getBotNumber()));
        return update(daBotInfo,queryWrapper);
    }
}
