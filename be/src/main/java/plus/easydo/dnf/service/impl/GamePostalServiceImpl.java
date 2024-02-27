package plus.easydo.dnf.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.dto.MailItemDto;
import plus.easydo.dnf.dto.SendMailDto;
import plus.easydo.dnf.dto.SignInConfigDate;
import plus.easydo.dnf.entity.Letter;
import plus.easydo.dnf.entity.Postal;
import plus.easydo.dnf.enums.AmplifyEnum;
import plus.easydo.dnf.enums.ItemTypeEnum;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.manager.LetterManager;
import plus.easydo.dnf.manager.PostalManager;
import plus.easydo.dnf.qo.RoleMailPageQo;
import plus.easydo.dnf.service.GamePostalService;
import plus.easydo.dnf.util.Latin1ConvertUtil;

import java.util.List;
import java.util.Objects;

import static plus.easydo.dnf.entity.table.PostalTableDef.POSTAL;


/**
 * @author laoyu
 * @version 1.0
 * @description 游戏邮箱服务实现
 * @date 2023/10/15
 */
@Service
@RequiredArgsConstructor
public class GamePostalServiceImpl implements GamePostalService {


    private final LetterManager letterManager;

    private final PostalManager postalManager;

    /**
     * 创建信件
     *
     * @param characNo characNo
     * @return plus.easydo.dnf.entity.Letter
     * @author laoyu
     * @date 2023/10/15
     */
    public Letter createLetter(Long characNo){
        //先注册信件主体
        Letter letter = new Letter();
        letter.setCharacNo(characNo);
        letter.setSendCharacNo(1);
        letter.setSendCharacName(Latin1ConvertUtil.convertLatin1("dnf-admin后台"));
        letter.setLetterText(Latin1ConvertUtil.convertLatin1("每日签到奖励,请查收。"));
        letter.setRegDate(LocalDateTimeUtil.now());
        letter.setStat(0);
        if(!letterManager.save(letter)){
            throw new BaseException("信件发送失败");
        }
        return letter;
    }


    @Override
    public boolean sendSignInRoleMail(Integer roleId, SignInConfigDate configData) {
//        List<SignInConfigDate.Conf> data = configData.getData();
//        Letter letter = createLetter(roleId);
//        data.forEach(conf -> sendPortal(letter,roleId,conf.getItemId(),conf.getItemType(),conf.getQuantity()));
        return true;
    }

    @Override
    public void sendMail(SendMailDto sendMailDto) {
        Letter letter = createLetter(sendMailDto.getCharacNo());
        List<MailItemDto> itemList = sendMailDto.getItemList();
        if(sendMailDto.getGold() > 0L){
            //先发送金币邮件
            sendPortal(letter,sendMailDto.getCharacNo(),0L,0,0L,sendMailDto.getGold());
        }
        //发送所有装备
        if(Objects.nonNull(itemList)){
            for (MailItemDto mailItemDto:itemList){
                sendPortal(letter,sendMailDto.getCharacNo(),mailItemDto.getItemId(),mailItemDto.getItemType(),mailItemDto.getCount(),0L);
            }
        }
    }

    @Override
    public boolean cleanCharacMail(Long characNo) {
        boolean res1 = letterManager.removeByCharacNo(characNo);
        boolean res2 = postalManager.removeByCharacNo(characNo);
        return res1 || res2;
    }

    @Override
    public boolean cleanMail() {
        boolean res1 = letterManager.clean();
        boolean res2 = postalManager.clean();
        return res1 || res2;
    }

    @Override
    public Page<Postal> roleMailPage(Long characNo, RoleMailPageQo pageQo) {
        QueryWrapper query = QueryWrapper.create().from(POSTAL).and(POSTAL.RECEIVE_CHARAC_NO.eq(characNo));
        return postalManager.page(new Page<>(pageQo.getCurrent(),pageQo.getPageSize()),query);
    }

    @Override
    public boolean removeMail(Long postalId) {
        return postalManager.removeById(postalId);
    }


    /**
     * 为角色发送邮件
     *
     * @param roleId roleId
     * @param itemId itemId
     * @param itemType itemType
     * @param quantity quantity
     * @author laoyu
     * @date 2023/10/15
     */
    public void sendPortal(Letter letter,Long roleId, Long itemId, Integer itemType, Long quantity,Long gold){
        //发送物品
        Postal postal = new Postal();
        //设置信件id
        postal.setLetterId(letter.getLetterId());
        //设置物品id
        postal.setItemId(itemId);
        //设置封装状态
        postal.setSealFlag(0);
        //设置数量和耐久度
        checkAndSetAddInfo(postal,itemType,quantity);
        //为装备时，表示为品级 数值与品级关系较为随机 时装是1
        postal.setEndurance(1);
        //设置收件角色
        postal.setReceiveCharacNo(roleId);
        postal.setSendCharacNo(0L);
        //发件人的姓名
        postal.setSendCharacName(Latin1ConvertUtil.convertLatin1("dnf-admin后台"));
        //增幅
        postal.setAmplifyOption(AmplifyEnum.EMPTY.getCode());
        postal.setAmplifyValue(0);
        //强化
        postal.setUpgrade(0);
        //锻造
        postal.setSeperateUpgrade(0);
        //金币
        postal.setGold(gold);
        // 上限
        postal.setUnlimitFlag(1);
        //打开标记
        checkAndSetAvataFlag(postal,itemType);
        //生物标识
        checkAndSetCreatureFlag(postal,itemType);
        //邮件发送时间
        postal.setOccTime(letter.getRegDate());
        postalManager.save(postal);
    }

    private void checkAndSetCreatureFlag(Postal postal, Integer itemType) {
        if(ItemTypeEnum.PET.getCode().equals(itemType)){
            postal.setCreatureFlag(1);
        }else {
            postal.setCreatureFlag(0);
        }
    }

    private void checkAndSetAvataFlag(Postal postal, Integer itemType) {
        if(ItemTypeEnum.FASHION.getCode().equals(itemType)){
            postal.setAvataFlag(1);
        }else {
            postal.setAvataFlag(0);
        }
    }

    private void checkAndSetAddInfo(Postal postal, Integer itemType,Long quantity){
        //道具材料等代表数量
        postal.setAddInfo(quantity);
        //时装固定是773
        if(ItemTypeEnum.FASHION.getCode().equals(itemType)){
            postal.setAddInfo(773L);
        }
        //装备代表耐久度、宠物未知, 先默认1
        if(ItemTypeEnum.EQUIPMENT.getCode().equals(itemType) || ItemTypeEnum.PET.getCode().equals(itemType)){
            postal.setAddInfo(1L);
        }
    }
}
