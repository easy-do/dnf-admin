package plus.easydo.dnf.manager;


import com.mybatisflex.core.service.IService;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.entity.CashCera;
import plus.easydo.dnf.mapper.CashCeraMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

import java.time.LocalDateTime;

/**
 * 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Component
public class CashCeraManager extends ServiceImpl<CashCeraMapper, CashCera> implements IService<CashCera> {

    public void regAccount(Long uid) {
        CashCera cashCera = CashCera.builder()
                .account(uid).cera(1000L).modTran(0L).modDate(LocalDateTime.now()).regDate(LocalDateTime.now()).build();
        save(cashCera);
    }
}
