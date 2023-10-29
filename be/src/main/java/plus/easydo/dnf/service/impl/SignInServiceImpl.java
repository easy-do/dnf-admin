package plus.easydo.dnf.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.easydo.dnf.dto.DaSignInConfDto;
import plus.easydo.dnf.dto.SignInConfigDate;
import plus.easydo.dnf.entity.CharacInfo;
import plus.easydo.dnf.entity.DaSignInConf;
import plus.easydo.dnf.entity.DaSignInLog;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.manager.DaSignInConfManager;
import plus.easydo.dnf.manager.DaSignInLogManager;
import plus.easydo.dnf.qo.DaSignInConfQo;
import plus.easydo.dnf.service.GamePostalService;
import plus.easydo.dnf.service.GameRoleService;
import plus.easydo.dnf.service.SignInService;

import java.util.List;
import java.util.Objects;


/**
 * @author laoyu
 * @version 1.0
 * @description 签到相关实现
 * @date 2023/10/14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SignInServiceImpl implements SignInService {

    private final DaSignInConfManager daSignInConfManager;

    private final DaSignInLogManager daSignInLogManager;

    private final GameRoleService gameRoleService;

    private final GamePostalService gamePostalService;

    @Override
    public List<DaSignInConf> signList(Integer characNo) {
        return daSignInConfManager.getRoleSignList(characNo);
    }

    @Override
    @Transactional
    public boolean pcCharacSign(Integer roleId) {
        checkUserCharac(roleId);
        DaSignInConf signInConf = daSignInConfManager.getByCurrentConf();
        if (Objects.isNull(signInConf)) {
            throw new BaseException("没有找到今日的签到配置");
        }

        if (daSignInLogManager.existRoleConfigLog(StpUtil.getLoginIdAsInt(),roleId,signInConf.getId())) {
            throw new BaseException("已经签到过了");
        }
        return saveLogAndSendMail(roleId, signInConf);
    }

    @Override
    public boolean characSign(Integer characNo) {
        DaSignInConf signInConf = daSignInConfManager.getByCurrentConf();
        if (Objects.isNull(signInConf)) {
            log.info("没有找到今日的签到配置.");
            return false;
        }

        if (daSignInLogManager.existRoleConfigLog(characNo,signInConf.getId())) {
            log.info("角色{}当日已签到.", characNo);
            return false;
        }
        return saveLogAndSendMail(characNo, signInConf);
    }

    private void checkUserCharac(Integer roleId) {
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
    }

    private boolean saveLogAndSendMail(Integer roleId, DaSignInConf signInConf) {
        DaSignInLog entity = new DaSignInLog();
        entity.setConfigId(signInConf.getId());
        entity.setSignInRoleId(roleId);
        entity.setCreateTime(LocalDateTimeUtil.now());
        boolean signInFlag = daSignInLogManager.save(entity);
        String configJsonStr = signInConf.getConfigJson();
        SignInConfigDate configData = JSONUtil.toBean(configJsonStr, SignInConfigDate.class);
        if(signInFlag){
            gamePostalService.sendSignInRoleMail(roleId,configData);
        }
        return signInFlag;
    }

    @Override
    public Page<DaSignInConf> signInPage(DaSignInConfQo daSignInConfQo) {
        return daSignInConfManager.pageByQo(daSignInConfQo);
    }

    @Override
    public DaSignInConf info(Long id) {
        return daSignInConfManager.getById(id);
    }

    @Override
    public boolean update(DaSignInConfDto daSignInConf) {
        return daSignInConfManager.save(BeanUtil.copyProperties(daSignInConf,DaSignInConf.class));
    }

    @Override
    public boolean insert(DaSignInConfDto daSignInConf) {
        if(daSignInConfManager.existsByDate(daSignInConf.getConfigDate())){
            throw new BaseException(daSignInConf.getConfigDate()+"的配置已存在");
        }
        return daSignInConfManager.save(BeanUtil.copyProperties(daSignInConf,DaSignInConf.class));
    }
}
