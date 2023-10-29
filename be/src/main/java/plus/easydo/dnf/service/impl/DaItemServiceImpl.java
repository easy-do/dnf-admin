package plus.easydo.dnf.service.impl;


import com.mybatisflex.core.paginate.Page;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.qo.PageQo;
import plus.easydo.dnf.service.IDaItemService;
import plus.easydo.dnf.entity.DaItemEntity;
import plus.easydo.dnf.mapper.DaItemMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

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
    public Page<DaItemEntity> itemPage(PageQo pageQo) {
        return page(new Page<>(pageQo.getPageNumber(),pageQo.getPageSize()));
    }

    @Override
    public List<DaItemEntity> listByName(String name) {
        return list(query().and(DA_ITEM_ENTITY.NAME.like(name)));
    }
}
