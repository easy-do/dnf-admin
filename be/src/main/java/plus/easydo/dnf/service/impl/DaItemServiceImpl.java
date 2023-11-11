package plus.easydo.dnf.service.impl;


import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.entity.DaItemEntity;
import plus.easydo.dnf.enums.RarityEnum;
import plus.easydo.dnf.mapper.DaItemMapper;
import plus.easydo.dnf.qo.DaItemQo;
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
    public Page<DaItemEntity> itemPage(DaItemQo daItemQo) {
        Page<DaItemEntity> page = new Page<>(daItemQo.getPageNumber(), daItemQo.getPageSize());
        QueryWrapper query = query()
                .and(DA_ITEM_ENTITY.NAME.like(daItemQo.getName()))
                .and(DA_ITEM_ENTITY.TYPE.like(daItemQo.getType()))
                .and(DA_ITEM_ENTITY.RARITY.like(daItemQo.getRarity()));
        return page(page, query);
    }

    @Override
    public List<DaItemEntity> listByName(String name) {
        return list(query().and(DA_ITEM_ENTITY.NAME.like(name)));
    }

    @Override
    public Long importItemForJson(List<JSONObject> res) {
        res.forEach(json -> {
            Long itemId = json.getLong("itemId");
            String name = json.getStr("name");
            if (CharSequenceUtil.isNotBlank(name)) {
                DaItemEntity entity = new DaItemEntity();
                entity.setId(itemId);
                entity.setName(name);
                entity.setType("道具");
                String rarity = json.getStr("rarity");
                if(CharSequenceUtil.isNotBlank(rarity)){
                    entity.setRarity(RarityEnum.getByCode(Integer.valueOf(rarity)));
                }
                if (!updateById(entity)) {
                    save(entity);
                }
            }

        });
        return null;
    }
}
