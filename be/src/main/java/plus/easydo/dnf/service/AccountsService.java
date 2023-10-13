package plus.easydo.dnf.service;


import plus.easydo.dnf.entity.Accounts;

/**
* @author yuzhanfeng
* @description 针对表【accounts】的数据库操作Service
* @createDate 2023-10-11 22:29:54
*/
public interface AccountsService {

    /**
     * 根据用户名查找用户
     *
     * @param userName userName
     * @return plus.easydo.dnf.entity.Accounts
     * @author laoyu
     * @date 2023/10/11
     */
    Accounts getByUserName(String userName);
}
