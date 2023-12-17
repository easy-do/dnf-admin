package plus.easydo.dnf.manager;

import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.entity.CharacInfo;
import plus.easydo.dnf.mapper.CharacInfoMapper;

import java.util.List;

import static plus.easydo.dnf.entity.table.CharacInfoTableDef.CHARAC_INFO;

/**
 * @author laoyu
 * @version 1.0
 * @description 游戏角色数据库操作类
 * @date 2023/10/16
 */
@Component
public class CharacInfoManager extends ServiceImpl<CharacInfoMapper,CharacInfo> {
    public List<CharacInfo> listByUid(Integer uid) {
        return QueryChain.of(getMapper())
                .select(CHARAC_INFO.ALL_COLUMNS)
                .from(CHARAC_INFO)
                .where(CHARAC_INFO.M_ID.ge(uid))
                .list();
    }
}
