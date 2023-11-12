package plus.easydo.dnf.service;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import plus.easydo.dnf.entity.DaGameConfigEntity;
import plus.easydo.dnf.qo.DaGameConfigQo;

/**
 * 游戏配置 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IDaGameConfigService extends IService<DaGameConfigEntity> {

    Page<DaGameConfigEntity> confPage(DaGameConfigQo gameConfigQo);

    boolean saveConf(DaGameConfigEntity daGameConfig);

    boolean updateConf(DaGameConfigEntity daGameConfig);

    DaGameConfigEntity getByConfKey(String confKey);
}
