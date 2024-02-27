package plus.easydo.dnf.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.easydo.dnf.dto.DaSignInConfDto;
import plus.easydo.dnf.dto.MailItemDto;
import plus.easydo.dnf.dto.SendMailDto;
import plus.easydo.dnf.dto.SignInConfigDate;
import plus.easydo.dnf.entity.CharacInfo;
import plus.easydo.dnf.entity.DaItemEntity;
import plus.easydo.dnf.entity.DaSignInConf;
import plus.easydo.dnf.entity.DaSignInLog;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.manager.DaSignInConfManager;
import plus.easydo.dnf.manager.DaSignInLogManager;
import plus.easydo.dnf.qo.DaSignInConfQo;
import plus.easydo.dnf.service.GameMailService;
import plus.easydo.dnf.service.GameRoleService;
import plus.easydo.dnf.service.IDaItemService;
import plus.easydo.dnf.service.SignInService;

import java.util.ArrayList;
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


    private final IDaItemService daItemService;

    private final GameMailService gameMailService;

    @Override
    public List<DaSignInConf> signList(Integer characNo) {
        return daSignInConfManager.getRoleSignList(characNo);
    }

    @Override
    @Transactional
    public boolean pcCharacSign(Long characNo) {
        checkUserCharac(characNo,StpUtil.getLoginIdAsLong());
        return characSign(characNo);
    }

    @Override
    public boolean botCharacSign(Long characNo, Long uid) {
        checkUserCharac(characNo,uid);
        return characSign(characNo);
    }

    private boolean characSign(Long characNo) {
        DaSignInConf signInConf = daSignInConfManager.getByCurrentConf();
        if (Objects.isNull(signInConf)) {
            throw new BaseException("没有找到今日的签到配置");
        }

        if (daSignInLogManager.existRoleConfigLog(characNo,signInConf.getId())) {
            throw new BaseException("已经签到过了");
        }
        return saveLogAndSendMail(null,characNo, signInConf);
    }

    @Override
    public void characSign(String channel, Long characNo) {
        DaSignInConf signInConf = daSignInConfManager.getByCurrentConf();
        if (Objects.isNull(signInConf)) {
            log.info("没有找到今日的签到配置.");
            return;
        }

        if (daSignInLogManager.existRoleConfigLog(characNo,signInConf.getId())) {
            log.info("角色{}当日已签到.", characNo);
            return;
        }
        saveLogAndSendMail(channel, characNo, signInConf);
    }

    private void checkUserCharac(Long characNo,Long uid) {
        List<CharacInfo> roleList = gameRoleService.roleList(uid, null);
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

    private boolean saveLogAndSendMail(String channel, Long characNo, DaSignInConf signInConf) {
        DaSignInLog entity = new DaSignInLog();
        entity.setConfigId(signInConf.getId());
        entity.setSignInRoleId(characNo);
        entity.setCreateTime(LocalDateTimeUtil.now());
        boolean signInFlag = daSignInLogManager.save(entity);
        String configJsonStr = signInConf.getConfigJson();

        List<SignInConfigDate> configData = JSONUtil.toBean(configJsonStr, new TypeReference<>() {
        }, true);
        if(signInFlag){
            if(configData.size() > 10){
                int page = 1;
                List<SignInConfigDate> current;
                do{
                    current = ListUtil.page(page, 10, configData);
                    sendMail(channel, characNo, current);
                    page++;
                }while (!current.isEmpty());

            }else {
                sendMail(channel, characNo, configData);
            }
        }
        return signInFlag;
    }

    private void sendMail(String channel, Long characNo, List<SignInConfigDate> current) {
        List<MailItemDto> itemList = new ArrayList<>();
        current.forEach(da-> itemList.add(MailItemDto.builder().itemId(da.getItemId()).itemType(da.getItemType()).count(da.getQuantity()).build()));
        SendMailDto dto = new SendMailDto();
        dto.setCharacNo(characNo);
        dto.setTitle("每日签到奖励-dnf-admin");
        dto.setContent("每日签到奖励,请查收. -dnf-admin");
        dto.setItemList(itemList);
        gameMailService.sendMail(dto);
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
    public boolean update(DaSignInConfDto daSignInConfDto) {
        DaSignInConf entity = dtoToEntity(daSignInConfDto);
        return daSignInConfManager.updateById(entity);
    }

    @Override
    public boolean insert(DaSignInConfDto daSignInConfDto) {
        if(daSignInConfManager.existsByDate(daSignInConfDto.getConfigDate())){
            throw new BaseException(daSignInConfDto.getConfigDate()+"的配置已存在");
        }
        DaSignInConf entity = dtoToEntity(daSignInConfDto);
        return daSignInConfManager.save(entity);
    }

    @Override
    public DaSignInConf getTodaySignConf() {
        return daSignInConfManager.getByCurrentConf();
    }

    private DaSignInConf dtoToEntity(DaSignInConfDto daSignInConfDto) {
        List<SignInConfigDate> configJson = daSignInConfDto.getConfigJson();
        if(Objects.isNull(configJson)){
            throw new BaseException("物品配置不能为空");
        }
        configJson.forEach(conf -> {
            Long id = conf.getItemId();
            DaItemEntity item = daItemService.getById(id);
            if(Objects.nonNull(item)){
                conf.setName(item.getName());
            }
        });
        DaSignInConf entity = BeanUtil.copyProperties(daSignInConfDto, DaSignInConf.class);
        entity.setConfigJson(JSONUtil.toJsonStr(configJson));
        return entity;
    }
}
