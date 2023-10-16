package plus.easydo.dnf.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.manager.AccountsManager;
import plus.easydo.dnf.service.AccountsService;


/**
* @author yuzhanfeng
* @description 针对表【accounts】的数据库操作Service实现
* @createDate 2023-10-11 22:29:54
*/
@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements AccountsService{

    private final AccountsManager accountsManager;

    @Override
    public Accounts getByUserName(String userName) {
       return accountsManager.getByUserName(userName);
    }
}




