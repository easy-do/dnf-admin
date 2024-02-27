package plus.easydo.dnf.service;


import com.mybatisflex.core.paginate.Page;
import plus.easydo.dnf.entity.DaGameEvent;
import com.mybatisflex.core.service.IService;
import plus.easydo.dnf.qo.DaGameEventQo;

import java.util.List;
import java.util.Map;

/**
 * 游戏事件 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface DaGameEventService extends IService<DaGameEvent> {

    Page<DaGameEvent> gameEventPage(DaGameEventQo daGameEventQo);

    boolean removeAll();

    Map<String,Integer> getMaxFileIndex();
}
