package plus.easydo.dnf.service;


import plus.easydo.dnf.dto.RegDto;
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

    /**
     * 注册账号
     *
     * @param regDto regDto
     * @return java.lang.String
     * @author laoyu
     * @date 2023-11-24
     */
    Long regAcc(RegDto regDto);

    /**
     * 充值点券
     *
     * @param type type
     * @param uid uid
     * @param count count
     * @return boolean
     * @author laoyu
     * @date 2023-11-24
     */
    boolean rechargeBonds(Integer type, Long uid, Long count);

    /**
     * 解封
     *
     * @param uid uid
     * @author laoyu
     * @date 2023-11-24
     */
    void enableAcc(Long uid);

    /**
     * 封禁
     *
     * @param uid uid
     * @author laoyu
     * @date 2023-11-24
     */
    void disableAcc(Long uid);
}
