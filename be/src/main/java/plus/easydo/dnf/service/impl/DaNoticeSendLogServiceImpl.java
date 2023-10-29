package plus.easydo.dnf.service.impl;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.qo.PageQo;
import plus.easydo.dnf.service.IDaNoticeSendLogService;
import plus.easydo.dnf.entity.DaNoticeSendLogEntity;
import plus.easydo.dnf.mapper.DaNoticeSendLogMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

import static plus.easydo.dnf.entity.table.DaNoticeSendLogEntityTableDef.DA_NOTICE_SEND_LOG_ENTITY;

/**
 * 公告发送记录 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class DaNoticeSendLogServiceImpl extends ServiceImpl<DaNoticeSendLogMapper, DaNoticeSendLogEntity> implements IDaNoticeSendLogService {

    @Override
    public Page<DaNoticeSendLogEntity> noticePage(PageQo pageQo) {
        Page<DaNoticeSendLogEntity> page = new Page<>(pageQo.getPageNumber(),pageQo.getPageSize());
        QueryWrapper query = query().orderBy(DA_NOTICE_SEND_LOG_ENTITY.CREATE_TIME, false);
        return page(page,query);
    }
}
