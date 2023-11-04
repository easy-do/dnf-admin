package plus.easydo.dnf.service.impl;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.entity.DaGameConfigEntity;
import plus.easydo.dnf.mapper.DaGameConfigMapper;
import plus.easydo.dnf.qo.DaGameConfigQo;
import plus.easydo.dnf.service.IDaGameConfigService;

import static plus.easydo.dnf.entity.table.DaGameConfigEntityTableDef.DA_GAME_CONFIG_ENTITY;

/**
 * 游戏配置 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class DaGameConfigServiceImpl extends ServiceImpl<DaGameConfigMapper, DaGameConfigEntity> implements IDaGameConfigService {

    @Override
    public Page<DaGameConfigEntity> confPage(DaGameConfigQo gameConfigQo) {
        QueryWrapper query = query()
                .and(DA_GAME_CONFIG_ENTITY.CONF_NAME.like(gameConfigQo.getConfName())
                        .and(DA_GAME_CONFIG_ENTITY.CONF_KEY.like(gameConfigQo.getConfKey())));
        return page(new Page<>(gameConfigQo.getPageNumber(), gameConfigQo.getPageSize()), query);
    }
}
