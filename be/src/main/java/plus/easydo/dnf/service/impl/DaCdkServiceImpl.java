package plus.easydo.dnf.service.impl;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.dto.MailItemDto;
import plus.easydo.dnf.dto.CdkConfDto;
import plus.easydo.dnf.dto.SendMailDto;
import plus.easydo.dnf.dto.UseCdkDto;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.qo.DaCdkQo;
import plus.easydo.dnf.service.AccountsService;
import plus.easydo.dnf.service.CaptchaService;
import plus.easydo.dnf.service.GameMailService;
import plus.easydo.dnf.service.IDaCdkService;
import plus.easydo.dnf.entity.DaCdk;
import plus.easydo.dnf.mapper.DaCdkMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import plus.easydo.dnf.util.ResponseUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static plus.easydo.dnf.entity.table.DaCdkTableDef.DA_CDK;

/**
 * cdk配置 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class DaCdkServiceImpl extends ServiceImpl<DaCdkMapper, DaCdk> implements IDaCdkService {

    private final GameMailService gameMailService;

    private final AccountsService accountsService;

    private final CaptchaService captchaService;

    @Override
    public List<String> add(DaCdk daCdk) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < daCdk.getNumber(); i++) {
            daCdk.setCreateTime(LocalDateTime.now());
            daCdk.setCreateBy(StpUtil.getLoginIdAsLong());
            daCdk.setCdkCode(null);
            save(daCdk);
            result.add(daCdk.getCdkCode());
        }
        return result;
    }

    @Override
    public Page<DaCdk> pageCdk(DaCdkQo cdkQo) {
        QueryWrapper queryWrapper = query().select(DA_CDK.CDK_CODE, DA_CDK.CREATE_TIME, DA_CDK.STATUS,DA_CDK.REMARK,DA_CDK.DELETE_FLAG)
                .and(DA_CDK.CDK_CODE.like(cdkQo.getCdkCode()))
                .and(DA_CDK.REMARK.like(cdkQo.getRemark()))
                .and(DA_CDK.STATUS.eq(cdkQo.getStatus()))
                .and(DA_CDK.CREATE_TIME.eq(cdkQo.getCreateTime()))
                .and(DA_CDK.DELETE_FLAG.eq(cdkQo.getDeleteFlag()))
                .orderBy(DA_CDK.CREATE_TIME,false);
        return page(new Page<>(cdkQo.getCurrent(),cdkQo.getPageSize()),queryWrapper);
    }

    @Override
    public Boolean removeCdk(List<String> ids) {
        DaCdk daCdk = new DaCdk();
        daCdk.setDeleteFlag(true);
        QueryWrapper query = query().and(DA_CDK.CDK_CODE.in(ids));
        return update(daCdk,query);
    }

    @Override
    public String useCdk(UseCdkDto useCdkDto) {
        if(!captchaService.verify(useCdkDto.getCaptchaKey(),useCdkDto.getVerificationCode())){
            throw new BaseException("验证码错误");
        }
        for (String cdk : useCdkDto.getCdks()) {
            useCdk(useCdkDto.getCharacNo(),cdk);
        }
        return "操作成功。";
    }

    @Override
    public void exPortCdk(List<String> ids, HttpServletResponse response) throws IOException {
        ServletOutputStream opt = response.getOutputStream();
        ResponseUtil.setFileResponse(response,"SDK导出.txt");
        List<DaCdk> list = listByIds(ids);
        for (DaCdk daCdk : list){
            String content = daCdk.getCdkCode()+" "+(daCdk.getStatus()?"已使用":"未使用")+" " +(daCdk.getDeleteFlag()?"已作废":"生效中")+" " + daCdk.getRemark()+ "\n";
            IoUtil.write(opt,false,content.getBytes(StandardCharsets.UTF_8));
        }
        IoUtil.close(opt);
    }

    private void useCdk(Long characNo,String cdk){
        DaCdk daCdk = getById(cdk.trim());
        if(Objects.isNull(daCdk)){
            throw new BaseException("cdk:" +cdk+",不存在。");
        }
        if(Boolean.TRUE.equals(daCdk.getStatus())){
            throw new BaseException("cdk:" +cdk+",已兑换。");
        }
        if(Boolean.TRUE.equals(daCdk.getDeleteFlag())){
            throw new BaseException("cdk:" +cdk+",已失效。");
        }
        String cdkConf = daCdk.getCdkConf();
        if(CharSequenceUtil.isBlank(cdkConf)){
            throw new BaseException("cdk:" +cdk+",配置信息为空,请联系管理员。");
        }
        CdkConfDto cdkConfDto = null;
        try {
            cdkConfDto = JSONUtil.toBean(cdkConf, CdkConfDto.class);
        }catch (Exception exception){
            throw new BaseException("cdk:" +cdk+",读取配置信息失败,请联系管理员。");
        }
        Long bonds = cdkConfDto.getBonds();
        //充值点券
        if(bonds > 0L){
            accountsService.rechargeBonds(1,StpUtil.getLoginIdAsLong(),bonds);
        }
        //发送邮件
        SendMailDto sendMailDto = new SendMailDto();
        sendMailDto.setCharacNo(characNo);
        sendMailDto.setGold(cdkConfDto.getGold());
        if(cdkConfDto.getItemId() >= 0L){
            MailItemDto mailItemDto = MailItemDto.builder()
                    .itemId(cdkConfDto.getItemId())
                    .itemType(cdkConfDto.getItemType())
                    .count(cdkConfDto.getItemQuantity())
                    .build();
            sendMailDto.setItemList(Collections.singletonList(mailItemDto));
        }
        sendMailDto.setTitle("dnf-admin");
        sendMailDto.setContent("SDK兑换奖励,请查收。");
        gameMailService.sendMail(sendMailDto);
        //设置cdk状态
        DaCdk entity = new DaCdk();
        entity.setCdkCode(cdk);
        entity.setStatus(true);
        entity.setUseTime(LocalDateTimeUtil.now());
        entity.setUseUser(StpUtil.getLoginIdAsLong());
        updateById(entity);
    }
}
