package plus.easydo.dnf.service;


import com.mybatisflex.core.paginate.Page;
import plus.easydo.dnf.entity.DaItemEntity;
import com.mybatisflex.core.service.IService;
import plus.easydo.dnf.qo.PageQo;

import java.util.List;

/**
 * 物品缓存 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IDaItemService extends IService<DaItemEntity> {

    Long importItem(DaItemEntity daItem);

    Page<DaItemEntity> itemPage(PageQo pageQo);

    List<DaItemEntity> listByName(String name);
}
