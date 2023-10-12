package plus.easydo.dnf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.service.AccountsService;
import plus.easydo.dnf.mapper.AccountsMapper;
import org.springframework.stereotype.Service;

/**
* @author yuzhanfeng
* @description 针对表【accounts】的数据库操作Service实现
* @createDate 2023-10-11 22:29:54
*/
@Service
public class AccountsServiceImpl extends ServiceImpl<AccountsMapper, Accounts>
    implements AccountsService{

    @Override
    public Accounts getByUserName(String userName) {
        return lambdaQuery().eq(Accounts::getAccountname,userName).one();
    }
}




