package plus.easydo.dnf.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.easydo.dnf.dto.DaSignInConfDto;
import plus.easydo.dnf.dto.SignInConfigDate;
import plus.easydo.dnf.entity.CharacInfo;
import plus.easydo.dnf.entity.DaItemEntity;
import plus.easydo.dnf.entity.DaSignInConf;
import plus.easydo.dnf.entity.DaSignInLog;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.manager.DaSignInConfManager;
import plus.easydo.dnf.manager.DaSignInLogManager;
import plus.easydo.dnf.qo.DaSignInConfQo;
import plus.easydo.dnf.service.GamePostalService;
import plus.easydo.dnf.service.GameRoleService;
import plus.easydo.dnf.service.IDaItemService;
import plus.easydo.dnf.service.SignInService;
import plus.easydo.dnf.util.ExecCallBuildUtil;
import plus.easydo.dnf.vo.CallResult;

import java.util.ArrayList;
import java.util.Collections;
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

    private final IDaItemService daItemService;

    @Override
    public List<DaSignInConf> signList(Integer characNo) {
        return daSignInConfManager.getRoleSignList(characNo);
    }

    @Override
    @Transactional
    public boolean pcCharacSign(Integer characNo) {
        checkUserCharac(characNo);
        DaSignInConf signInConf = daSignInConfManager.getByCurrentConf();
        if (Objects.isNull(signInConf)) {
            throw new BaseException("没有找到今日的签到配置");
        }

        if (daSignInLogManager.existRoleConfigLog(characNo,signInConf.getId())) {
            throw new BaseException("已经签到过了");
        }
        return saveLogAndSendMail(null, characNo, signInConf);
    }

    @Override
    public boolean characSign(String opt, Integer characNo) {
        DaSignInConf signInConf = daSignInConfManager.getByCurrentConf();
        if (Objects.isNull(signInConf)) {
            log.info("没有找到今日的签到配置.");
            return false;
        }

        if (daSignInLogManager.existRoleConfigLog(characNo,signInConf.getId())) {
            log.info("角色{}当日已签到.", characNo);
            return false;
        }
        return saveLogAndSendMail(opt,characNo, signInConf);
    }

    private void checkUserCharac(Integer characNo) {
        List<CharacInfo> roleList = gameRoleService.roleList(StpUtil.getLoginIdAsInt(), null);
        CharacInfo currentRole = null;
        for (CharacInfo characInfo: roleList) {
            if(characNo.equals(characInfo.getCharacNo())){
                currentRole = characInfo;
            }
        }
        if (Objects.isNull(currentRole)) {
            throw new BaseException("没有找到对应角色");
        }
    }

    private boolean saveLogAndSendMail(String opt, Integer characNo, DaSignInConf signInConf) {
        DaSignInLog entity = new DaSignInLog();
        entity.setConfigId(signInConf.getId());
        entity.setSignInRoleId(characNo);
        entity.setCreateTime(LocalDateTimeUtil.now());
        boolean signInFlag = daSignInLogManager.save(entity);
        String configJsonStr = signInConf.getConfigJson();
        SignInConfigDate configData = JSONUtil.toBean(configJsonStr, SignInConfigDate.class);
        if(signInFlag){
            List<SignInConfigDate.Conf> data = configData.getData();
            String tile = "每日签到奖励-dnf-admin";
            String content = "每日签到奖励,请查收. -dnf-admin";
            if(data.size() > 10){
                data.forEach(da->{
                    List<Long> itemconfList = new ArrayList<>();
                    itemconfList.add(da.getItemId());
                    itemconfList.add(da.getQuantity());
                    CallResult callResult = ExecCallBuildUtil.buildSendMultiMail(characNo, tile, content, 0L, Collections.singletonList(itemconfList));
                    CacheManager.addExecList(opt,callResult);
                });
            }else {
                List<List<Long>> itemList = new ArrayList<>();
                data.forEach(da->{
                    List<Long> itemconfList = new ArrayList<>();
                    itemconfList.add(da.getItemId());
                    itemconfList.add(da.getQuantity());
                    itemList.add(itemconfList);
                });
                CallResult callResult = ExecCallBuildUtil.buildSendMultiMail(characNo,tile,content,0L,itemList);
                CacheManager.addExecList(opt,callResult);
            }

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
        String jsonStr = daSignInConf.getConfigJson();
        if(CharSequenceUtil.isBlank(jsonStr)){
            throw new BaseException("物品配置不能为空");
        }
        SignInConfigDate configData = JSONUtil.toBean(jsonStr, SignInConfigDate.class);
        List<SignInConfigDate.Conf> confs = configData.getData();
        confs.forEach(conf -> {
            Long id = conf.getItemId();
            DaItemEntity item = daItemService.getById(id);
            if(Objects.nonNull(item)){
                conf.setName(item.getName());
            }
        });
        daSignInConf.setConfigJson(JSONUtil.toJsonStr(configData));
        return daSignInConfManager.updateById(BeanUtil.copyProperties(daSignInConf,DaSignInConf.class));
    }

    @Override
    public boolean insert(DaSignInConfDto daSignInConf) {
        if(daSignInConfManager.existsByDate(daSignInConf.getConfigDate())){
            throw new BaseException(daSignInConf.getConfigDate()+"的配置已存在");
        }
        String jsonStr = daSignInConf.getConfigJson();
        if(CharSequenceUtil.isBlank(jsonStr)){
            throw new BaseException("物品配置不能为空");
        }
        SignInConfigDate configData = JSONUtil.toBean(jsonStr, SignInConfigDate.class);
        List<SignInConfigDate.Conf> confs = configData.getData();
        confs.forEach(conf -> {
            Long id = conf.getItemId();
            DaItemEntity item = daItemService.getById(id);
            if(Objects.nonNull(item)){
                conf.setName(item.getName());
            }
        });
        daSignInConf.setConfigJson(JSONUtil.toJsonStr(configData));
        return daSignInConfManager.save(BeanUtil.copyProperties(daSignInConf,DaSignInConf.class));
    }
}
