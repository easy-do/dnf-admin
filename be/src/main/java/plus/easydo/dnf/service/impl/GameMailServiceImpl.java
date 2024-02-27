package plus.easydo.dnf.service.impl;

import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.dto.SendMailDto;
import plus.easydo.dnf.entity.CharacInfo;
import plus.easydo.dnf.entity.DaItemEntity;
import plus.easydo.dnf.entity.DaMailSendLog;
import plus.easydo.dnf.entity.Postal;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.qo.RoleMailPageQo;
import plus.easydo.dnf.service.GameMailService;
import plus.easydo.dnf.service.GamePostalService;
import plus.easydo.dnf.service.IDaItemService;
import plus.easydo.dnf.service.IDaMailSendLogService;
import plus.easydo.dnf.util.WebSocketUtil;

import java.time.LocalDateTime;
import java.util.Objects;


/**
 * @author yuzhanfeng
 * @Date 2024-01-05 10:48
 * @Description 游戏邮件
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GameMailServiceImpl implements GameMailService {

    private final IDaMailSendLogService iDaMailSendLogService;

    private final GamePostalService gamePostalService;

    private final IDaItemService daItemService;


    @Override
    public void sendMail(SendMailDto sendMailDto) {
        if(WebSocketUtil.getSessionCount() > 0L){
            WebSocketUtil.sendMail(sendMailDto);
        }else {
            log.warn("frida客户端全部离线,使用数据库发送邮件");
            gamePostalService.sendMail(sendMailDto);
        }
        iDaMailSendLogService.save(DaMailSendLog.builder().sendDetails(JSONUtil.toJsonStr(sendMailDto)).createTime(LocalDateTime.now()).build());
    }


    @Override
    public Page<Postal> roleMailPage(Long characNo, RoleMailPageQo pageQo) {
        Page<Postal> res = gamePostalService.roleMailPage(characNo, pageQo);
        res.getRecords().forEach(postal -> {
            Long itemId = postal.getItemId();
            DaItemEntity item = daItemService.getItemInfoCache(itemId);
            if(Objects.nonNull(item)){
                postal.setItemName(item.getName());
            }
            CharacInfo characInfo = CacheManager.CHARAC_INFO_CACHE.get(postal.getSendCharacNo());
            if(Objects.nonNull(characInfo)){
                postal.setSendCharacName(characInfo.getCharacName());
            }
        });
        return res;
    }

    @Override
    public boolean removeMail(Long postalId) {
        return gamePostalService.removeMail(postalId);
    }
}
