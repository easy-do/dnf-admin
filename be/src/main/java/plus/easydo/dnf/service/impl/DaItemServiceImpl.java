package plus.easydo.dnf.service.impl;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.entity.DaItemEntity;
import plus.easydo.dnf.mapper.DaItemMapper;
import plus.easydo.dnf.qo.DaItemQo;
import plus.easydo.dnf.qo.PageQo;
import plus.easydo.dnf.service.IDaItemService;

import java.util.List;

import static plus.easydo.dnf.entity.table.DaItemEntityTableDef.DA_ITEM_ENTITY;

/**
 * 物品缓存 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class DaItemServiceImpl extends ServiceImpl<DaItemMapper, DaItemEntity> implements IDaItemService {

    @Override
    public Long importItem(DaItemEntity daItem) {

        return null;
    }

    @Override
    public Page<DaItemEntity> itemPage(DaItemQo daItemQo) {
        Page<DaItemEntity> page = new Page<>(daItemQo.getPageNumber(), daItemQo.getPageSize());
        QueryWrapper query = query()
                .and(DA_ITEM_ENTITY.NAME.like(daItemQo.getName()))
                .and(DA_ITEM_ENTITY.TYPE.like(daItemQo.getType()))
                .and(DA_ITEM_ENTITY.RARITY.like(daItemQo.getRarity()));
        return page(page,query);
    }

    @Override
    public List<DaItemEntity> listByName(String name) {
        return list(query().and(DA_ITEM_ENTITY.NAME.like(name)));
    }
}
