package plus.easydo.dnf.service.impl;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.entity.DaMailSendLogEntity;
import plus.easydo.dnf.mapper.DaMailSendLogMapper;
import plus.easydo.dnf.qo.PageQo;
import plus.easydo.dnf.service.IDaMailSendLogService;

import static plus.easydo.dnf.entity.table.DaMailSendLogEntityTableDef.DA_MAIL_SEND_LOG_ENTITY;

/**
 * 邮件发送记录 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class DaMailSendLogServiceImpl extends ServiceImpl<DaMailSendLogMapper, DaMailSendLogEntity> implements IDaMailSendLogService {

    @Override
    public Page<DaMailSendLogEntity> sendLogpage(PageQo pageQo) {
        QueryWrapper query = query().orderBy(DA_MAIL_SEND_LOG_ENTITY.CREATE_TIME, false);
        return page(new Page<>(pageQo.getPageNumber(),pageQo.getPageSize()),query);
    }
}
