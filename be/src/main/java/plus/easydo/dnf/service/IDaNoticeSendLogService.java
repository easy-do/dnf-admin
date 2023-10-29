package plus.easydo.dnf.service;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import plus.easydo.dnf.entity.DaNoticeSendLogEntity;
import plus.easydo.dnf.qo.PageQo;

/**
 * 公告发送记录 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IDaNoticeSendLogService extends IService<DaNoticeSendLogEntity> {

    Page<DaNoticeSendLogEntity> noticePage(PageQo pageQo);
}
