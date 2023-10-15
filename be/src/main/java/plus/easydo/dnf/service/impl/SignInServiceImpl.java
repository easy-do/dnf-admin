package plus.easydo.dnf.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.easydo.dnf.dto.SignInConfigDate;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.entity.CharacInfo;
import plus.easydo.dnf.entity.DaSignInConf;
import plus.easydo.dnf.entity.DaSignInLog;
import plus.easydo.dnf.entity.Letter;
import plus.easydo.dnf.entity.Postal;
import plus.easydo.dnf.enums.AmplifyEnum;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.mapper.DaSignInConfMapper;
import plus.easydo.dnf.mapper.DaSignInLogMapper;
import plus.easydo.dnf.mapper.LetterMapper;
import plus.easydo.dnf.security.CurrentUserContextHolder;
import plus.easydo.dnf.service.GamePostalService;
import plus.easydo.dnf.service.GameRoleService;
import plus.easydo.dnf.service.SignInService;
import plus.easydo.dnf.util.LocalDateTimeUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static cn.hutool.json.XMLTokener.entity;
import static plus.easydo.dnf.entity.table.DaSignInConfTableDef.DA_SIGN_IN_CONF;
import static plus.easydo.dnf.entity.table.DaSignInLogTableDef.DA_SIGN_IN_LOG;

/**
 * @author laoyu
 * @version 1.0
 * @description 签到相关实现
 * @date 2023/10/14
 */
@Service
@RequiredArgsConstructor
public class SignInServiceImpl implements SignInService {

    private final DaSignInConfMapper daSignInConfMapper;

    private final DaSignInLogMapper daSignInLogMapper;

    private final GameRoleService gameRoleService;

    private final GamePostalService gamePostalService;

    @Override
    public List<DaSignInConf> signList(Integer roleId) {
        Accounts user = CurrentUserContextHolder.getCurrentUser();
        QueryWrapper query = new QueryWrapper()
                .select(DA_SIGN_IN_CONF.ALL_COLUMNS
                        , DA_SIGN_IN_LOG.CREATE_TIME.as("signInTime"))
                .from(DA_SIGN_IN_CONF)
                .leftJoin(DA_SIGN_IN_LOG)
                .on(DA_SIGN_IN_CONF.ID.eq(DA_SIGN_IN_LOG.CONFIG_ID)
                        .and(DA_SIGN_IN_LOG.SIGN_IN_ROLE_ID.eq(roleId))
                        .and(DA_SIGN_IN_LOG.SIGN_IN_USER_ID.eq(user.getUid())))
                .where(DA_SIGN_IN_CONF.CONFIG_DATE.between(LocalDateTimeUtils.monthStartTime(), LocalDateTimeUtils.monthEndTime()));
        return daSignInConfMapper.selectListByQuery(query);
    }

    @Override
    @Transactional
    public boolean roleSign(Integer roleId) {
        Accounts user = CurrentUserContextHolder.getCurrentUser();
        List<CharacInfo> roleList = gameRoleService.roleList();
        CharacInfo currentRole = null;
        for (CharacInfo characInfo: roleList) {
            if(roleId.equals(characInfo.getCharacNo())){
                currentRole = characInfo;
            }
        }
        if (Objects.isNull(currentRole)) {
            throw new BaseException("没有找到对应角色");
        }
        String date = LocalDateTimeUtil.format(LocalDateTimeUtil.now(), DatePattern.NORM_DATE_FORMATTER);
        QueryWrapper query = new QueryWrapper().where(DA_SIGN_IN_CONF.CONFIG_DATE.eq(date));
        DaSignInConf signInConf = daSignInConfMapper.selectOneByQuery(query);
        if (Objects.isNull(signInConf)) {
            throw new BaseException("没有找到今日的签到配置");
        }

        long logCount = daSignInLogMapper.selectCountByQuery(QueryWrapper.create()
                .from(DA_SIGN_IN_LOG).where(DA_SIGN_IN_LOG.SIGN_IN_ROLE_ID.eq(roleId)
                        .and(DA_SIGN_IN_LOG.SIGN_IN_USER_ID.eq(user.getUid())).and(DA_SIGN_IN_LOG.CONFIG_ID.eq(signInConf.getId()))));
        if (logCount > 0L) {
            throw new BaseException("已经签到过了");
        }
        DaSignInLog entity = new DaSignInLog();
        entity.setConfigId(signInConf.getId());
        entity.setSignInUserId(user.getUid());
        entity.setSignInRoleId(roleId);
        entity.setCreateTime(LocalDateTimeUtil.now());
        boolean signInFlag = daSignInLogMapper.insert(entity) == 1;
        String configJsonStr = signInConf.getConfigJson();
        SignInConfigDate configData = JSONUtil.toBean(configJsonStr, SignInConfigDate.class);
        if(signInFlag){
            gamePostalService.sendSignInRoleMail(currentRole.getCharacNo(),configData);
        }
        return signInFlag;
    }
}
