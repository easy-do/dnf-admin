package plus.easydo.dnf.util;

import cn.hutool.extra.spring.SpringUtil;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.entity.CashCera;
import plus.easydo.dnf.entity.CashCeraPoint;
import plus.easydo.dnf.service.AccountsService;

import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-02-26 12:42
 * @Description 机器人账号相关操作工具类
 */
public class BotAccountUtils {


    private static AccountsService accountsService;

    private BotAccountUtils() {
    }

    public static AccountsService getAccountsService(){
        if(Objects.isNull(accountsService)){
            return SpringUtil.getBean(AccountsService.class);
        }
        return accountsService;
    }

    public static Long getUidByUserName(String userName){
        Accounts account = getAccountsService().getByUserName(userName);
        if(Objects.nonNull(account)){
            return account.getUid();
        }
        return null;
    }

    /**
     * 充值
     *
     * @param type type
     * @param uid uid
     * @param count count
     * @return boolean
     * @author laoyu
     * @date 2024-02-26
     */
    public static boolean rechargeBonds(Integer type, Long uid, Long count){
       return getAccountsService().rechargeBonds(type, uid, count);
    }

    /**
     * 点券余额
     *
     * @param uid uid
     * @return plus.easydo.dnf.entity.CashCera
     * @author laoyu
     * @date 2024-02-26
     */
    public static CashCera getCashCera(Long uid){
        return getAccountsService().getCashCera(uid);
    }

    /**
     * 代币券余额
     *
     * @param uid uid
     * @return plus.easydo.dnf.entity.CashCeraPoint
     * @author laoyu
     * @date 2024-02-26
     */
    public static CashCeraPoint getCashCeraPoint(Long uid){
        return getAccountsService().getCashCeraPoint(uid);
    }

}
