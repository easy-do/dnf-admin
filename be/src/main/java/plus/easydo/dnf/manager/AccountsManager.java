package plus.easydo.dnf.manager;

import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.mapper.AccountsMapper;

import static plus.easydo.dnf.entity.table.AccountsTableDef.ACCOUNTS;

/**
 * @author laoyu
 * @version 1.0
 * @description 账户数据库操作类
 * @date 2023/10/16
 */
@Component
public class AccountsManager extends ServiceImpl<AccountsMapper, Accounts> {
    public Accounts getByUserName(String userName) {
        return QueryChain.of(getMapper())
                .select(ACCOUNTS.ALL_COLUMNS)
                .from(ACCOUNTS)
                .where(ACCOUNTS.ACCOUNTNAME.eq(userName))
                .one();
    }

    public boolean existsByUserName(String userName) {
        QueryWrapper query = query().and(ACCOUNTS.ACCOUNTNAME.eq(userName));
        return exists(query);
    }

    public Accounts getByQq(String qq) {
        QueryWrapper query = query().and(ACCOUNTS.QQ.eq(qq));
        return getOne(query);
    }
}
