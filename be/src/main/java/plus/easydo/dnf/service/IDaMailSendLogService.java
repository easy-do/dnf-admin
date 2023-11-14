package plus.easydo.dnf.service;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import plus.easydo.dnf.entity.DaMailSendLog;
import plus.easydo.dnf.qo.PageQo;

/**
 * 邮件发送记录 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IDaMailSendLogService extends IService<DaMailSendLog> {

    Page<DaMailSendLog> sendLogpage(PageQo pageQo);
}
