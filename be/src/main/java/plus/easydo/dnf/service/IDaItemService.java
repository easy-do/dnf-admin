package plus.easydo.dnf.service;


import cn.hutool.json.JSONObject;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import plus.easydo.dnf.entity.DaItemEntity;
import plus.easydo.dnf.qo.DaItemQo;

import java.util.List;
import java.util.Map;

/**
 * 物品缓存 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IDaItemService extends IService<DaItemEntity> {

    Page<DaItemEntity> itemPage(DaItemQo daItemQo);

    List<DaItemEntity> listByName(String name);

    Long importItemForJson(List<JSONObject> res);

    void importItemForMap(Map<Integer, String> itemMap);
}
