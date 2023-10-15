package plus.easydo.dnf.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.dto.SignInConfigDate;
import plus.easydo.dnf.entity.Letter;
import plus.easydo.dnf.entity.Postal;
import plus.easydo.dnf.enums.AmplifyEnum;
import plus.easydo.dnf.enums.ItemTypeEnum;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.mapper.LetterMapper;
import plus.easydo.dnf.mapper.PostalMapper;
import plus.easydo.dnf.service.GamePostalService;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 游戏邮箱服务实现
 * @date 2023/10/15
 */
@Service
@RequiredArgsConstructor
public class GamePostalServiceImpl implements GamePostalService {


    private final LetterMapper letterMapper;

    private final PostalMapper postalMapper;

    /**
     * 创建信件
     *
     * @param characNo characNo
     * @return plus.easydo.dnf.entity.Letter
     * @author laoyu
     * @date 2023/10/15
     */
    public Letter createLetter(Integer characNo){
        //先注册信件主体
        Letter letter = new Letter();
        letter.setCharacNo(characNo);
        letter.setSendCharacNo(1);
        letter.setSendCharacName("admin");
        letter.setLetterText("每日签到奖励");
        letter.setRegDate(LocalDateTimeUtil.now());
        letter.setStat(0);
        if(letterMapper.insert(letter) == 0){
            throw new BaseException("信件发送失败");
        }
        return letter;
    }


    @Override
    public boolean sendSignInRoleMail(Integer roleId, SignInConfigDate configData) {
        List<SignInConfigDate.Conf> data = configData.getData();
        Letter letter = createLetter(roleId);
        data.forEach(conf -> sendPortal(letter,roleId,conf.getItemId(),conf.getItemType(),conf.getQuantity()));
        return true;
    }

    public void sendPortal(Integer roleId, Long itemId, Integer itemType, Integer quantity){
        Letter letter = createLetter(roleId);
        sendPortal(letter,roleId,itemId, itemType, quantity);
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
    public void sendPortal(Letter letter,Integer roleId,Long itemId, Integer itemType, Integer quantity){
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
        postal.setSendCharacNo(0);
        postal.setSendCharacName("DNF-ADMIN");
        //增幅
        postal.setAmplifyOption(AmplifyEnum.EMPTY.getCode());
        postal.setAmplifyValue(0);
        //强化
        postal.setUpgrade(0);
        //锻造
        postal.setSeperateUpgrade(0);
        //金币
        postal.setGold(0L);
        // 上限
        postal.setUnlimitFlag(1);
        //打开标记
        checkAndSetAvataFlag(postal,itemType);
        //生物标识
        checkAndSetCreatureFlag(postal,itemType);
        //邮件发送时间
        postal.setOccTime(letter.getRegDate());
        postalMapper.insert(postal);
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

    private void checkAndSetAddInfo(Postal postal, Integer itemType,Integer quantity){
        //道具材料等代表数量
        postal.setAddInfo(quantity);
        //时装固定是773
        if(ItemTypeEnum.FASHION.getCode().equals(itemType)){
            postal.setAddInfo(773);
        }
        //装备代表耐久度、宠物未知, 先默认1
        if(ItemTypeEnum.EQUIPMENT.getCode().equals(itemType) || ItemTypeEnum.PET.getCode().equals(itemType)){
            postal.setAddInfo(1);
        }
    }
}
