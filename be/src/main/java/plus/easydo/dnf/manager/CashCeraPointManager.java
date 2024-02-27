package plus.easydo.dnf.manager;


import com.mybatisflex.core.service.IService;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.entity.CashCeraPoint;
import plus.easydo.dnf.mapper.CashCeraPointMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

import java.time.LocalDateTime;

/**
 * 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class CashCeraPointManager extends ServiceImpl<CashCeraPointMapper, CashCeraPoint> implements IService<CashCeraPoint> {

    public void regAccount(Long uid) {
        CashCeraPoint cashCeraPoint = CashCeraPoint.builder()
                .account(uid).ceraPoint(1000L).modDate(LocalDateTime.now()).regDate(LocalDateTime.now()).build();
        save(cashCeraPoint);
    }
}
