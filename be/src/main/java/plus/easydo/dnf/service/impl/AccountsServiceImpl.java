package plus.easydo.dnf.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.dto.RegDto;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.manager.AccountsManager;
import plus.easydo.dnf.qo.AccountsQo;
import plus.easydo.dnf.qo.PageQo;
import plus.easydo.dnf.service.AccountsService;
import plus.easydo.dnf.util.FlexDataSourceUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import static plus.easydo.dnf.entity.table.AccountsTableDef.ACCOUNTS;


/**
 * @author yuzhanfeng
 * @description 针对表【accounts】的数据库操作Service实现
 * @createDate 2023-10-11 22:29:54
 */
@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements AccountsService {

    private final AccountsManager accountsManager;

    public final FlexDataSourceUtil flexDataSourceUtil;

    @Override
    public Accounts getByUserName(String userName) {
        return accountsManager.getByUserName(userName);
    }

    @Override
    public Long regAcc(RegDto regDto) {
        if (accountsManager.existsByUserName(regDto.getUserName())) {
            throw new BaseException("账号已存在");
        }
        Accounts acc = new Accounts();
        acc.setAccountname(regDto.getUserName());
        acc.setPassword(SecureUtil.md5().digestHex(regDto.getPassword()));
        acc.setVip("");
        if (accountsManager.save(acc)) {
            try {
                DruidDataSource taiwan = flexDataSourceUtil.getDataSource("d_taiwan");
                Connection conn = taiwan.getConnection();
                Statement stat = conn.createStatement();
                stat.execute("INSERT INTO d_taiwan.member_info(m_id, user_id)VALUES('" + acc.getUid() + "', '" + acc.getUid() + "')");
                stat.execute("INSERT INTO d_taiwan.member_white_account(m_id)VALUES('" + acc.getUid() + "')");
                stat.execute("INSERT INTO taiwan_login.member_login(m_id)VALUES('" + acc.getUid() + "')");
                stat.execute("INSERT INTO taiwan_cain_2nd.member_avatar_coin(m_id)VALUES('" + acc.getUid() + "')");
                stat.execute("INSERT INTO taiwan_billing.cash_cera(account, cera,mod_tran, mod_date, reg_date)VALUES('" + acc.getUid() + "', 1000, 0, NOW(), NOW())");
                stat.execute("INSERT INTO taiwan_billing.cash_cera_point(account, cera_point,mod_date, reg_date)VALUES('" + acc.getUid() + "', 0, NOW(), NOW())");
                stat.close();
                conn.close();
            } catch (SQLException e) {
                throw new BaseException("注册失败:{}", ExceptionUtil.getMessage(e));
            }
        }
        return acc.getUid();
    }

    @Override
    public boolean rechargeBonds(Integer type, Long uid, Long count) {
        try {
            DruidDataSource taiwanBilling = flexDataSourceUtil.getDataSource("taiwan_billing");
            Connection conn = taiwanBilling.getConnection();
            Statement stat = conn.createStatement();
            if (type == 2) {
                //代币券
                stat.execute("update taiwan_billing.cash_cera_point SET cera_point = cera_point + 10 where account='" + uid + "';");
            } else {
                //点券
                stat.execute("update taiwan_billing.cash_cera set cera = cera + " + count + " where account='" + uid + "'");
            }
            stat.close();
            conn.close();
        } catch (SQLException e) {
            throw new BaseException("充值点券失败:{}", ExceptionUtil.getMessage(e));
        }
        return false;
    }

    @Override
    public void enableAcc(Long uid) {
        try {
            DruidDataSource dTaiwan = flexDataSourceUtil.getDataSource("d_taiwan");
            Connection conn = dTaiwan.getConnection();
            Statement stat = conn.createStatement();
            stat.execute("delete from d_taiwan.member_punish_info where m_id='" + uid + "'");
            stat.close();
            conn.close();
        } catch (SQLException e) {
            throw new BaseException("解封操作失败:{}", ExceptionUtil.getMessage(e));
        }
    }

    @Override
    public void disableAcc(Long uid) {
        try {
            DruidDataSource dTaiwan = flexDataSourceUtil.getDataSource("d_taiwan");
            Connection conn = dTaiwan.getConnection();
            Statement stat = conn.createStatement();
            stat.execute("insert into d_taiwan.member_punish_info (m_id,punish_type,occ_time,punish_value,apply_flag,start_time,end_time,reason) values ('"+uid+"','1',NOW(),'101','2',NOW(), date_sub(NOW(),interval -3650 day),'GM')");
            stat.close();
            conn.close();
        } catch (SQLException e) {
            throw new BaseException("封禁操作失败:{}", ExceptionUtil.getMessage(e));
        }
    }

    @Override
    public boolean save(Accounts accounts) {
        return accountsManager.save(accounts);
    }

    @Override
    public boolean update(Accounts accounts) {
        return accountsManager.updateById(accounts);
    }

    @Override
    public Accounts getById(Long id) {
        Accounts accounts = accountsManager.getById(id);
        accounts.setPassword("");
        return accounts;
    }

    @Override
    public Page<Accounts> page(AccountsQo pageQo) {
        Page<Accounts> page = new Page<>(pageQo.getCurrent(),pageQo.getPageSize());
        QueryWrapper queryWrapper = QueryChain.create()
                .select(ACCOUNTS.UID, ACCOUNTS.ACCOUNTNAME, ACCOUNTS.QQ, ACCOUNTS.BILLING, ACCOUNTS.VIP)
                .from(ACCOUNTS).where(ACCOUNTS.UID.eq(pageQo.getUid())
                        .and(ACCOUNTS.ACCOUNTNAME.eq(pageQo.getAccountname()))
                        .and(ACCOUNTS.QQ.eq(pageQo.getQq())))
                .and(ACCOUNTS.VIP.eq(pageQo.getVip()));
        return accountsManager.page(page,queryWrapper);
    }

    @Override
    public boolean resetPassword(Long uid, String password) {
        Accounts accounts = accountsManager.getById(uid);
        if(Objects.isNull(accounts)){
            throw new BaseException("账户不存在");
        }
        accounts.setPassword(SecureUtil.md5().digestHex(password));
        return accountsManager.updateById(accounts);
    }
}




