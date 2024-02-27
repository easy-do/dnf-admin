package plus.easydo.dnf.service;


import com.mybatisflex.core.paginate.Page;
import plus.easydo.dnf.dto.RegDto;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.entity.CashCera;
import plus.easydo.dnf.entity.CashCeraPoint;
import plus.easydo.dnf.qo.AccountsQo;

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
    boolean enableAcc(Long uid);

    /**
     * 封禁
     *
     * @param uid uid
     * @author laoyu
     * @date 2023-11-24
     */
    boolean disableAcc(Long uid);


    /**
     * 更新
     *
     * @param accounts accounts
     * @return boolean
     * @author laoyu
     * @date 2023-11-29
     */
    boolean update(Accounts accounts);

    /**
     * 详情
     *
     * @param id id
     * @return plus.easydo.dnf.entity.Accounts
     * @author laoyu
     * @date 2023-11-29
     */
    Accounts getById(Long id);

    /**
     * 分页查询
     *
     * @param page page
     * @return com.mybatisflex.core.paginate.Page<plus.easydo.dnf.entity.Accounts>
     * @author laoyu
     * @date 2023-11-29
     */
    Page<Accounts> page(AccountsQo page);

    /**
     * 重置密码
     *
     * @param uid uid
     * @param password password
     * @return boolean
     * @author laoyu
     * @date 2023-11-29
     */
    boolean resetPassword(Long uid, String password);

    /**
     * 重置角色创建限制
     *
     * @param uid uid
     * @return boolean
     * @author laoyu
     * @date 2024/1/7
     */
    boolean resetCreateRole(Long uid);

    /**
     * 设置角色栏最大
     *
     * @param uid uid
     * @return java.lang.Boolean
     * @author laoyu
     * @date 2024/1/7
     */
    Boolean setMaxRole(Long uid);

    Long count();

    Boolean openDungeon(Long uid);

    CashCera getCashCera(Long uid);

    CashCeraPoint getCashCeraPoint(Long uid);
}
