package plus.easydo.dnf.service.impl;

import com.mybatisflex.core.query.QueryChain;
import lombok.RequiredArgsConstructor;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.mapper.AccountsMapper;
import plus.easydo.dnf.service.AccountsService;
import org.springframework.stereotype.Service;

import static plus.easydo.dnf.entity.table.AccountsTableDef.ACCOUNTS;

/**
* @author yuzhanfeng
* @description 针对表【accounts】的数据库操作Service实现
* @createDate 2023-10-11 22:29:54
*/
@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements AccountsService{

    private final AccountsMapper accountsMapper;

    @Override
    public Accounts getByUserName(String userName) {
       return QueryChain.of(accountsMapper)
                .select(ACCOUNTS.ALL_COLUMNS)
                .from(ACCOUNTS)
                .where(ACCOUNTS.ACCOUNTNAME.ge(userName))
                .one();
    }
}




