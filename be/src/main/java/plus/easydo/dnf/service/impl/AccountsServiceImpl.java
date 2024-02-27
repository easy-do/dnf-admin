package plus.easydo.dnf.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.crypto.SecureUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.constant.SystemConstant;
import plus.easydo.dnf.dto.RegDto;
import plus.easydo.dnf.entity.*;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.manager.AccountsManager;
import plus.easydo.dnf.manager.CashCeraManager;
import plus.easydo.dnf.manager.CashCeraPointManager;
import plus.easydo.dnf.manager.CharacViewManager;
import plus.easydo.dnf.manager.LimitCreateCharacterManager;
import plus.easydo.dnf.manager.MemberDungeonManager;
import plus.easydo.dnf.manager.MemberPunishInfoManager;
import plus.easydo.dnf.mapper.MemberAvatarCoinMapper;
import plus.easydo.dnf.mapper.MemberInfoMapper;
import plus.easydo.dnf.mapper.MemberLoginMapper;
import plus.easydo.dnf.mapper.MemberWhiteAccountMapper;
import plus.easydo.dnf.qo.AccountsQo;
import plus.easydo.dnf.service.AccountsService;
import plus.easydo.dnf.service.CaptchaService;
import plus.easydo.dnf.util.FlexDataSourceUtil;

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

    private final CaptchaService captchaService;

    private final LimitCreateCharacterManager limitCreateCharacterManager;

    private final MemberPunishInfoManager memberPunishInfoManager;

    private final CharacViewManager characViewManager;

    private final MemberInfoMapper memberInfoMapper;

    private final MemberWhiteAccountMapper memberWhiteAccountMapper;

    private final MemberLoginMapper memberLoginMapper;

    private final MemberAvatarCoinMapper memberAvatarCoinMapper;

    private final CashCeraManager cashCeraManager;

    private final CashCeraPointManager cashCeraPointManager;

    private final MemberDungeonManager memberDungeonManager;


    @Override
    public Accounts getByUserName(String userName) {
        return accountsManager.getByUserName(userName);
    }

    @Override
    public Long regAcc(RegDto regDto) {
        if(!captchaService.verify(regDto.getCaptchaKey(),regDto.getVerificationCode())){
            throw new BaseException("验证码错误。");
        }
        if (accountsManager.existsByUserName(regDto.getUserName())) {
            throw new BaseException("账号已存在");
        }
        Accounts acc = new Accounts();
        acc.setAccountname(regDto.getUserName());
        acc.setPassword(SecureUtil.md5().digestHex(regDto.getPassword()));
        acc.setVip("");
        if (accountsManager.save(acc)) {
            memberInfoMapper.insert(MemberInfo.builder().mId(acc.getUid()).userId(acc.getUid()).build(), true);
            memberWhiteAccountMapper.insert(MemberWhiteAccount.builder().mId(acc.getUid()).build(), true);
            memberLoginMapper.insert(MemberLogin.builder().mId(acc.getUid()).build(), true);
            memberAvatarCoinMapper.insert(MemberAvatarCoin.builder().mId(acc.getUid()).build(), true);
            cashCeraManager.regAccount(acc.getUid());
            cashCeraPointManager.regAccount(acc.getUid());
        }
        return acc.getUid();
    }

    @Override
    public boolean rechargeBonds(Integer type, Long uid, Long count) {
            if (type == 2) {
                //代币券
                CashCeraPoint cashCeraPoint = cashCeraPointManager.getById(uid);
                if (Objects.isNull(cashCeraPoint)) {
                    throw new BaseException("未找到账号充值信息");
                }
                cashCeraPoint.setCeraPoint(cashCeraPoint.getCeraPoint() + count);
                cashCeraPointManager.updateById(cashCeraPoint);
            } else {
                //点券
                CashCera cashCera = cashCeraManager.getById(uid);
                if (Objects.isNull(cashCera)) {
                    throw new BaseException("未找到账号充值信息");
                }
                cashCera.setCera(cashCera.getCera() + count);
                cashCeraManager.updateById(cashCera);
            }
        return true;
    }

    @Override
    public boolean enableAcc(Long uid) {
        return memberPunishInfoManager.removeById(uid);
    }

    @Override
    public boolean disableAcc(Long uid) {
        MemberPunishInfo entity = new MemberPunishInfo();
        entity.setMId(uid);
        entity.setPunishType(1);
        entity.setOccTime(LocalDateTimeUtil.now());
        entity.setPunishValue(101);
        entity.setApplyFlag(1);
        entity.setStartTime(LocalDateTimeUtil.now());
        entity.setEndTime("9999-12-31 23:59:59");
        entity.setReason("GM");
        return memberPunishInfoManager.save(entity);
    }

    @Override
    public boolean update(Accounts accounts) {
        accounts.setPassword(null);
        return accountsManager.updateById(accounts);
    }

    @Override
    public Accounts getById(Long id) {
        Accounts accounts = accountsManager.getById(id);
        if(Objects.nonNull(accounts)){
            accounts.setPassword("");
        }
        return accounts;
    }

    @Override
    public Page<Accounts> page(AccountsQo pageQo) {
        Page<Accounts> page = new Page<>(pageQo.getCurrent(),pageQo.getPageSize());
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(ACCOUNTS.UID, ACCOUNTS.ACCOUNTNAME, ACCOUNTS.QQ, ACCOUNTS.VIP)
                .from(ACCOUNTS).where(ACCOUNTS.UID.like(pageQo.getUid())
                        .and(ACCOUNTS.ACCOUNTNAME.like(pageQo.getAccountname()))
                        .and(ACCOUNTS.QQ.like(pageQo.getQq())))
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

    @Override
    public boolean resetCreateRole(Long uid) {
        LimitCreateCharacter entity = new LimitCreateCharacter();
        entity.setMId(uid);
        entity.setCount(0);
        boolean res = limitCreateCharacterManager.updateById(entity);
        if(res){
            memberPunishInfoManager.removeById(uid);
        }
        return res;
    }

    @Override
    public Boolean setMaxRole(Long uid) {
        CharacView entity = new CharacView();
        entity.setMId(uid);
        entity.setCharacCount(24);
        entity.setCharacSlotLimit(24);
        return characViewManager.updateById(entity);
    }

    @Override
    public Long count() {
        return accountsManager.count();
    }

    @Override
    public Boolean openDungeon(Long uid) {
        MemberDungeon entity = new MemberDungeon();
        entity.setDungeon(SystemConstant.OPEN_MEMBER_DUNGEON_LEVEL);
        entity.setMId(uid);
        return memberDungeonManager.updateById(entity);
    }

    @Override
    public CashCera getCashCera(Long uid) {
        return cashCeraManager.getById(uid);
    }

    @Override
    public CashCeraPoint getCashCeraPoint(Long uid) {
        return cashCeraPointManager.getById(uid);
    }
}




