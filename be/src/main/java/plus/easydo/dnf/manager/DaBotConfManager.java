package plus.easydo.dnf.manager;


import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.entity.DaBotConf;
import plus.easydo.dnf.mapper.DaBotConfMapper;

import java.util.List;

import static plus.easydo.dnf.entity.table.DaBotConfTableDef.DA_BOT_CONF;

/**
 * 机器人配置 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class DaBotConfManager extends ServiceImpl<DaBotConfMapper, DaBotConf> {

    public List<DaBotConf> getByBotNumber(String botNumber) {
        QueryWrapper queryWrapper = query().and(DA_BOT_CONF.BOT_NUMBER.eq(botNumber));
        return list(queryWrapper);
    }

    public DaBotConf getByBotNumberAndKey(String botNumber, String key) {
        QueryWrapper queryWrapper = query().and(DA_BOT_CONF.BOT_NUMBER.eq(botNumber)).and(DA_BOT_CONF.CONF_KEY.eq(key));
        List<DaBotConf> res = list(queryWrapper);
        if(!res.isEmpty()){
            return res.get(0);
        }
        return null;
    }
}
