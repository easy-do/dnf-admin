package plus.easydo.dnf.service.impl;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.entity.DaNoticeSendLog;
import plus.easydo.dnf.mapper.DaNoticeSendLogMapper;
import plus.easydo.dnf.qo.PageQo;
import plus.easydo.dnf.service.IDaNoticeSendLogService;

import static plus.easydo.dnf.entity.table.DaNoticeSendLogTableDef.DA_NOTICE_SEND_LOG;

/**
 * 公告发送记录 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class DaNoticeSendLogServiceImpl extends ServiceImpl<DaNoticeSendLogMapper, DaNoticeSendLog> implements IDaNoticeSendLogService {

    @Override
    public Page<DaNoticeSendLog> noticePage(PageQo pageQo) {
        Page<DaNoticeSendLog> page = new Page<>(pageQo.getCurrent(),pageQo.getPageSize());
        QueryWrapper query = query().orderBy(DA_NOTICE_SEND_LOG.CREATE_TIME, false);
        return page(page,query);
    }
}
