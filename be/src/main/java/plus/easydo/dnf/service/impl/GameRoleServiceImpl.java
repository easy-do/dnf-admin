package plus.easydo.dnf.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.dto.AccountCargoDto;
import plus.easydo.dnf.dto.EditGameRoleItemDto;
import plus.easydo.dnf.dto.OtherDataDto;
import plus.easydo.dnf.entity.*;
import plus.easydo.dnf.enums.ExpertJobJobEnum;
import plus.easydo.dnf.enums.RoleItemTypeEnum;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.manager.AccountCargoManager;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.manager.CharacInfoManager;
import plus.easydo.dnf.manager.CharacInvenExpandManager;
import plus.easydo.dnf.manager.CharacQuestShopManager;
import plus.easydo.dnf.manager.MemberAvatarCoinManager;
import plus.easydo.dnf.manager.PvpResultManager;
import plus.easydo.dnf.manager.SkillManager;
import plus.easydo.dnf.manager.UserItemsManager;
import plus.easydo.dnf.mapper.CharacStatMapper;
import plus.easydo.dnf.mapper.Event1306AccountRewardMapper;
import plus.easydo.dnf.mapper.LoginAccount3Mapper;
import plus.easydo.dnf.qo.CharacInfoQo;
import plus.easydo.dnf.service.GameRoleService;
import plus.easydo.dnf.service.IDaItemService;
import plus.easydo.dnf.service.IInventoryService;
import plus.easydo.dnf.util.DbItemReaderUtil;
import plus.easydo.dnf.util.DictUtil;
import plus.easydo.dnf.util.FlexDataSourceUtil;
import plus.easydo.dnf.util.Latin1ConvertUtil;
import plus.easydo.dnf.util.ResultSetUtil;
import plus.easydo.dnf.vo.GameItemVo;
import plus.easydo.dnf.vo.RoleItemVo;
import plus.easydo.dnf.vo.OtherDataVo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static plus.easydo.dnf.entity.table.CharacInfoTableDef.CHARAC_INFO;
import static plus.easydo.dnf.entity.table.CharacStatTableDef.CHARAC_STAT;
import static plus.easydo.dnf.entity.table.Event1306AccountRewardTableDef.EVENT1306_ACCOUNT_REWARD;
import static plus.easydo.dnf.entity.table.LoginAccount3TableDef.LOGIN_ACCOUNT3;
import static plus.easydo.dnf.entity.table.UserItemsTableDef.USER_ITEMS;


/**
 * @author laoyu
 * @version 1.0
 * @description 游戏角色相关实现
 * @date 2023/10/14
 */
@Service
@RequiredArgsConstructor
public class GameRoleServiceImpl implements GameRoleService {

    private static final JSONObject jobDictJson = JSONUtil.parseObj(DictUtil.JOB_DICT);

    private final FlexDataSourceUtil flexDataSourceUtil;

    private final IInventoryService inventoryService;

    private final CharacInfoManager characInfoManager;

    private final CharacStatMapper characStatMapper;

    private final LoginAccount3Mapper loginAccount3Mapper;

    private final Event1306AccountRewardMapper event1306AccountRewardMapper;

    private final PvpResultManager pvpResultManager;

    private final SkillManager skillManager;

    private final MemberAvatarCoinManager memberAvatarCoinManager;

    private final CharacQuestShopManager characQuestShopManager;

    private final UserItemsManager userItemsManager;

    private final IDaItemService daItemService;

    private final CharacInvenExpandManager characInvenExpandManager;

    private final AccountCargoManager accountCargoManager;

    public List<CharacInfo> allRole() {
        List<CharacInfo> list;
        try {
            DruidDataSource ds = flexDataSourceUtil.getDataSource("taiwan_cain");
            Connection conn = ds.getConnection();
            Statement stat = conn.createStatement();
            conn.prepareStatement("SET NAMES latin1").execute();
            String sql = "SELECT charac_no,charac_name FROM charac_info";
            ResultSet rs = stat.executeQuery(sql);
            list = ResultSetUtil.reToBeanList(rs, CharacInfo.class, Collections.singletonList("charac_name"));
            stat.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<CharacInfo> roleList(Long uid, String name) {
        List<CharacInfo> list;
        try {
            DruidDataSource ds = flexDataSourceUtil.getDataSource("taiwan_cain");
            Connection conn = ds.getConnection();
            Statement stat = conn.createStatement();
            conn.prepareStatement("SET NAMES latin1").execute();
            String sql = "SELECT * FROM charac_info";
            if (Objects.nonNull(uid) || CharSequenceUtil.isNotBlank(name)) {
                sql = sql + " where ";
            }
            if (Objects.nonNull(uid)) {
                sql = sql + "m_id = " + uid;
            }
            if (CharSequenceUtil.isNotBlank(name)) {
                if (Objects.nonNull(uid)) {
                    sql = sql + " and ";
                }
                sql = sql + "charac_name like " + Latin1ConvertUtil.convertLatin1(name);
            }
            ResultSet rs = stat.executeQuery(sql);
            list = ResultSetUtil.reToBeanList(rs, CharacInfo.class, Collections.singletonList("charac_name"));
            stat.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        list.forEach(characInfo -> {
            Integer job = characInfo.getJob();
            Integer growType = characInfo.getGrowType();
            Object jobName = jobDictJson.getByPath(job + "." + growType);
            characInfo.setJobName(String.valueOf(jobName));
            Integer expertJob = characInfo.getExpertJob();
            characInfo.setExpertJobName(String.valueOf(ExpertJobJobEnum.getNameByCode(expertJob)));
        });
        return list;
    }

    @Override
    public RoleItemVo getRoleItem(Long characNo, String type) {
        RoleItemVo vo = new RoleItemVo();
        if(type.equals(RoleItemTypeEnum.ALL.getType()) || !type.equals(RoleItemTypeEnum.CARGO.getType())){
            Inventory inventoryEntity = inventoryService.getById(characNo);
            if (Objects.isNull(inventoryEntity)) {
                throw new BaseException("没有找到角色背包");
            }
            if(type.equals(RoleItemTypeEnum.EQ.getType())){
                //穿戴装备
                List<GameItemVo> equipSlot = DbItemReaderUtil.readerItem(inventoryEntity.getEquipslot());
                setItemName(equipSlot);
                vo.setEquipslot(equipSlot);
            }
            if(type.equals(RoleItemTypeEnum.INVENTORY.getType())){
                // 背包物品
                List<GameItemVo> inventory = DbItemReaderUtil.readerItem(inventoryEntity.getInventory());
                setItemName(inventory);
                vo.setInventory(inventory);
            }
            if(type.equals(RoleItemTypeEnum.CREATURE.getType())){
                // 宠物栏
                List<GameItemVo> creature = DbItemReaderUtil.readerItem(inventoryEntity.getCreature());
                setItemName(creature);
                vo.setCreature(creature);
            }
        }
        if(type.equals(RoleItemTypeEnum.ALL.getType()) || type.equals(RoleItemTypeEnum.CARGO.getType())){
            // 仓库
            CharacInvenExpand characInvenExpand = characInvenExpandManager.getById(characNo);
            List<GameItemVo> cargo = DbItemReaderUtil.readerItem(characInvenExpand.getCargo());
            setItemName(cargo);
            vo.setCargo(cargo);
        }
        if(type.equals(RoleItemTypeEnum.ALL.getType()) || type.equals(RoleItemTypeEnum.ACCOUNT_CARGO.getType())){
            // 账号金库
            AccountCargo accountCargo = getAccountCargo(characNo);
            List<GameItemVo> cargo = DbItemReaderUtil.readerItem(accountCargo.getCargo());
            setItemName(cargo);
            vo.setAccountCargo(cargo);
        }
        return vo;
    }

    private void setItemName(List<GameItemVo> gameItemVoList){
        gameItemVoList.forEach(gameItemVo -> {
            DaItemEntity daItem = daItemService.getItemInfoCache((long) gameItemVo.getItemId());
            if(Objects.nonNull(daItem)){
                gameItemVo.setItemName(daItem.getName());
            }
        });
    }

    @PostConstruct
    @Override
    public void initCharacCache() {
        Map<Long, CharacInfo> map = allRole().stream().collect(Collectors.toMap(CharacInfo::getCharacNo, (c) -> c));
        CacheManager.CHARAC_INFO_CACHE.putAll(map);
    }

    public CharacInfo getCharacInfoCache(Long characNo) {
        CharacInfo cache = CacheManager.CHARAC_INFO_CACHE.get(characNo);
        if (Objects.isNull(cache)) {
            initCharacCache();
            cache = CacheManager.CHARAC_INFO_CACHE.get(characNo);
        }
        return cache;
    }

    @Override
    public Page<CharacInfo> page(CharacInfoQo characInfoQo) {

        QueryWrapper query = QueryWrapper.create().from(CHARAC_INFO)
                .select(CHARAC_INFO.M_ID, CHARAC_INFO.CHARAC_NO, CHARAC_INFO.CHARAC_NAME, CHARAC_INFO.DELETE_FLAG,CHARAC_INFO.CREATE_TIME)
                .and(CHARAC_INFO.M_ID.eq(characInfoQo.getMId())
                        .and(CHARAC_INFO.CHARAC_NO.eq(characInfoQo.getCharacNo())));
        if (CharSequenceUtil.isNotBlank(characInfoQo.getCharacName())) {
            query.and(CHARAC_INFO.CHARAC_NAME.like(Latin1ConvertUtil.convertLatin1(characInfoQo.getCharacName())));
        }
        if (Objects.nonNull(characInfoQo.getOnline()) && characInfoQo.getOnline()) {
            //在线的账号
            QueryWrapper query1 = QueryWrapper.create()
                    .from(LOGIN_ACCOUNT3).select(LOGIN_ACCOUNT3.M_ID).and(LOGIN_ACCOUNT3.LOGIN_STATUS.eq(1));
            List<LoginAccount3> onlineList = loginAccount3Mapper.selectListByQuery(query1);
            if (onlineList.isEmpty()) {
                return new Page<>();
            }
            //找到在线账号对应的角色
            List<Integer> mids = onlineList.stream().map(LoginAccount3::getMId).toList();
            QueryWrapper query2 = QueryWrapper.create().from(EVENT1306_ACCOUNT_REWARD).select(EVENT1306_ACCOUNT_REWARD.M_ID)
                    .and(EVENT1306_ACCOUNT_REWARD.M_ID.in(mids));
            List<Event1306AccountReward> event1306AccountRewards = event1306AccountRewardMapper.selectListByQuery(query2);
            if (event1306AccountRewards.isEmpty()) {
                return new Page<>();
            }
            List<Integer> characNos = event1306AccountRewards.stream().map(Event1306AccountReward::getCharacNo).toList();
            query.and(CHARAC_INFO.CHARAC_NO.in(characNos));
        }
        Page<CharacInfo> page = characInfoManager.page(new Page<>(characInfoQo.getCurrent(), characInfoQo.getPageSize()), query);
        if (!page.getRecords().isEmpty()) {
            List<CharacInfo> records = page.getRecords();
            records.forEach(characInfo -> {
                CharacInfo cacheCharacInfo = getCharacInfoCache(characInfo.getCharacNo());
                if (Objects.nonNull(cacheCharacInfo)) {
                    characInfo.setCharacName(cacheCharacInfo.getCharacName());
                }
            });
        }
        return page;
    }

    @Override
    public CharacInfo info(Long characNo) {
        CharacInfo characInfo = getCharacInfo(characNo);
        characInfo.setCharacName(getCharacInfoCache(characInfo.getCharacNo()).getCharacName());
        return characInfo;
    }

    @Override
    public boolean update(CharacInfo characInfo) {
        if (CharSequenceUtil.isNotBlank(characInfo.getCharacName())) {
            characInfo.setCharacName(Latin1ConvertUtil.convertLatin1(characInfo.getCharacName()));
        }
        return characInfoManager.updateById(characInfo);
    }

    @Override
    public boolean remove(Long characNo) {
        if (Objects.nonNull(characNo)) {
            CharacInfo characInfo = new CharacInfo();
            characInfo.setCharacNo(characNo);
            characInfo.setDeleteFlag(1);
            return characInfoManager.updateById(characInfo);
        }
        return false;
    }

    @Override
    public boolean recover(Long characNo) {
        if (Objects.nonNull(characNo)) {
            CharacInfo characInfo = new CharacInfo();
            characInfo.setCharacNo(characNo);
            characInfo.setDeleteFlag(0);
            return characInfoManager.updateById(characInfo);
        }
        return false;
    }

    @Override
    public boolean updateRoleItem(EditGameRoleItemDto editGameRoleItemDto) {
        String type = editGameRoleItemDto.getType();
        Long characNo = editGameRoleItemDto.getCharacNo();
        Inventory inventory;
        switch (type) {
            case "eq" -> {
                inventory = getInventory(characNo);
                return inventoryService.updateById(Inventory.builder().characNo(inventory.getCharacNo()).equipslot(buildItemByte(inventory.getEquipslot(), editGameRoleItemDto.getGameItemVo())).build());
            }
            case "inventory" -> {
                inventory = getInventory(characNo);
                return inventoryService.updateById(Inventory.builder().characNo(inventory.getCharacNo()).inventory(buildItemByte(inventory.getInventory(), editGameRoleItemDto.getGameItemVo())).build());
            }
            case "creature" -> {
                inventory = getInventory(characNo);
                return inventoryService.updateById(Inventory.builder().characNo(inventory.getCharacNo()).inventory(buildItemByte(inventory.getCreature(), editGameRoleItemDto.getGameItemVo())).build());
            }
            case "cargo" -> {
                CharacInvenExpand characInvenExpand = characInvenExpandManager.getById(characNo);
                if (Objects.isNull(characInvenExpand)) {
                    throw new BaseException("没有找到角色仓库信息");
                }
                return characInvenExpandManager.updateById(CharacInvenExpand.builder().characNo(characNo).cargo(buildItemByte(characInvenExpand.getCargo(), editGameRoleItemDto.getGameItemVo())).build());
            }
            case "account_cargo" -> {
                AccountCargo accountCargo = getAccountCargo(characNo);
                return accountCargoManager.updateById(AccountCargo.builder().mId(accountCargo.getMId()).cargo(buildItemByte(accountCargo.getCargo(), editGameRoleItemDto.getGameItemVo())).build());
            }
            default -> {
                // do nothing
            }
        }
        return false;
    }

    private CharacInfo getCharacInfo(Long characNo){
        CharacInfo characInfo = characInfoManager.getById(characNo);
        if(Objects.isNull(characInfo)){
            throw new BaseException("角色不存在");
        }
        return characInfo;
    }

    @Override
    public AccountCargo getAccountCargo(Long characNo) {
        CharacInfo characInfo = getCharacInfo(characNo);
        AccountCargo accountCargo = accountCargoManager.getById(characInfo.getMId());
        if(Objects.isNull(accountCargo)){
            throw new BaseException("没有找到或未开通账号金库");
        }
        return accountCargo;
    }

    @Override
    public boolean createAccountCargo(Long characNo) {
        CharacInfo characInfo = getCharacInfo(characNo);
        AccountCargo accountCargo = accountCargoManager.getById(characInfo.getMId());
        if(Objects.nonNull(accountCargo)){
            throw new BaseException("已开通账号金库");
        }
        AccountCargo entity = AccountCargo.builder().mId(characInfo.getMId()).money(0).capacity(8).cargo(new byte[0]).occTime(LocalDateTime.now()).build();
        return accountCargoManager.save(entity);
    }

    @Override
    public boolean removeAccountCargo(Long characNo) {
        CharacInfo characInfo = getCharacInfo(characNo);
        return accountCargoManager.removeById(characInfo.getMId());
    }

    @Override
    public boolean updateAccountCargo(AccountCargoDto accountCargoDto) {
        AccountCargo accountCargo = accountCargoManager.getById(accountCargoDto.getMId());
        if(Objects.isNull(accountCargo)){
            throw new BaseException("没有找到或未开通账号金库");
        }
        accountCargo.setMoney(accountCargoDto.getMoney());
        accountCargo.setCapacity(accountCargoDto.getCapacity());
        accountCargo.setOccTime(null);
        accountCargo.setCargo(null);
        return accountCargoManager.updateById(accountCargo);
    }

    private Inventory getInventory(Long characNo) {
        Inventory inventory = inventoryService.getById(characNo);
        if (Objects.isNull(inventory)) {
            throw new BaseException("没有找到角色物品信息");
        }
        return inventory;
    }

    @Override
    public boolean openLeftAndRight(Long characNo) {
        CharacStat entity1 = CharacStat.builder()
                .addSlotFlag(3)
                .build();
        QueryWrapper query = QueryWrapper.create().from(CHARAC_STAT).and(CHARAC_STAT.CHARAC_NO.eq(characNo));
        return characStatMapper.updateByQuery(entity1, true, query) > 0;
    }

    @Override
    public Long onlineCount() {
        QueryWrapper query = QueryWrapper.create()
                .from(LOGIN_ACCOUNT3).and(LOGIN_ACCOUNT3.LOGIN_STATUS.eq(1));
        return loginAccount3Mapper.selectCountByQuery(query);
    }

    @Override
    public OtherDataVo getOtherData(Long characNo) {
        PvpResult pvpResult = pvpResultManager.getById(characNo);
        OtherDataVo otherDataVo = new OtherDataVo();
        if(Objects.nonNull(pvpResult)){
            BeanUtil.copyProperties(pvpResult, otherDataVo);
        }
        Inventory inventory = inventoryService.getById(characNo);
        if(Objects.nonNull(pvpResult)){
            otherDataVo.setMoney(inventory.getMoney());
        }
        Skill skill = skillManager.getById(characNo);
        if(Objects.nonNull(skill)){
            otherDataVo.setSp(skill.getRemainSp());
            otherDataVo.setTp(skill.getRemainSfp1st());
        }
        CharacQuestShop questShop = characQuestShopManager.getById(characNo);
        if(Objects.nonNull(questShop)){
            otherDataVo.setQp(questShop.getQp());
        }
        CharacInfo characInfo = getCharacInfo(characNo);
        MemberAvatarCoin memberAvatarCoin = memberAvatarCoinManager.getById(characInfo.getMId());
        if(Objects.nonNull(memberAvatarCoin)){
            otherDataVo.setAvatarCoin(memberAvatarCoin.getAvatarCoin());
        }
        return otherDataVo;
    }

    @Override
    public boolean setOtherData(OtherDataDto otherDataDto) {
        OtherDataDto oldData = otherDataDto.getOldData();
        if(Objects.isNull(oldData)){
            throw new BaseException("参数校验失败");
        }
        Long characNo = otherDataDto.getCharacNo();
        if(!oldData.getPvpGrade().equals(otherDataDto.getPvpGrade()) || !oldData.getPvpPoint().equals(otherDataDto.getPvpPoint()) || !oldData.getWin().equals(otherDataDto.getWin())){
           pvpResultManager.updateById(PvpResult.builder().characNo(characNo).pvpGrade(otherDataDto.getPvpGrade()).pvpPoint(otherDataDto.getPvpPoint()).win(otherDataDto.getWin()).build());
        }
        if(!oldData.getMoney().equals(otherDataDto.getMoney())){
            inventoryService.updateById(Inventory.builder().characNo(characNo).money(otherDataDto.getMoney()).build());
        }
        if(!oldData.getSp().equals(otherDataDto.getSp()) || !oldData.getTp().equals(otherDataDto.getTp()) ){
            skillManager.updateById(Skill.builder().characNo(characNo).remainSp(otherDataDto.getSp()).remainSfp1st(otherDataDto.getTp()).build());
        }
        if(Objects.nonNull(oldData.getQp()) && (!oldData.getQp().equals(otherDataDto.getQp()))){
            characQuestShopManager.updateById(CharacQuestShop.builder().characNo(characNo).qp(otherDataDto.getQp()).build());
        }
        if(!oldData.getAvatarCoin().equals(otherDataDto.getAvatarCoin())){
            CharacInfo characInfo = getCharacInfo(characNo);
            memberAvatarCoinManager.updateById(MemberAvatarCoin.builder().mId(characInfo.getMId()).avatarCoin(otherDataDto.getAvatarCoin()).build());
        }
        return true;
    }

    @Override
    public boolean cleanItem(Long characNo, Long type) {
        CharacInfo characInfo = getCharacInfo(characNo);
        Inventory inventory;
        if (type == 1) {
            inventory = getInventory(characNo);
            return inventoryService.updateById(Inventory.builder().characNo(characNo).equipslot(buildEmptyByte(inventory.getEquipslot())).build());
        } else if (type == 2) {
            inventory = getInventory(characNo);
            return inventoryService.updateById(Inventory.builder().characNo(characNo).inventory(buildEmptyByte(inventory.getInventory())).build());
        } else if (type == 3) {
            inventory = getInventory(characNo);
            return inventoryService.updateById(Inventory.builder().characNo(characNo).creature(buildEmptyByte(inventory.getCreature())).build());
        } else if (type == 4) {
            CharacInvenExpand characInvenExpand = characInvenExpandManager.getById(characNo);
            if (Objects.isNull(characInvenExpand)) {
                throw new BaseException("没有找到角色仓库信息");
            }
            return characInvenExpandManager.updateById(CharacInvenExpand.builder().characNo(characNo).cargo(buildEmptyByte(characInvenExpand.getCargo())).build());
        } else if (type == 5) {
            return accountCargoManager.updateById(AccountCargo.builder().mId(characInfo.getMId()).cargo(new byte[0]).build());
        }
        return false;
    }

    @Override
    public List<UserItems> roleItems(Long characNo) {
        QueryWrapper query = QueryWrapper.create().from(USER_ITEMS).and(USER_ITEMS.CHARAC_NO.eq(characNo)).orderBy(USER_ITEMS.SLOT,true);
        List<UserItems> itemsList = userItemsManager.list(query);
        itemsList.forEach(items->{
            DaItemEntity daItem = daItemService.getItemInfoCache(items.getItId());
            if(Objects.nonNull(daItem)){
                items.setItemName(daItem.getName());
            }
        });
        return itemsList;
    }

    @Override
    public boolean removeItems(Long uiId) {
        return userItemsManager.removeById(uiId);
    }

    @Override
    public boolean cleanItems(Long characNo) {
        QueryWrapper query = QueryWrapper.create().from(USER_ITEMS).and(USER_ITEMS.CHARAC_NO.eq(characNo));
        return userItemsManager.remove(query);
    }

    private byte[] buildItemByte(byte[] data, GameItemVo editData) {
        List<GameItemVo> list = DbItemReaderUtil.readerItem(data);
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byte[] startByte = new byte[4];
        byteBuffer.get(startByte);
        GameItemVo dbItem = list.get(editData.getIndex());
        CopyOptions copyOptions = CopyOptions.create();
        copyOptions.setIgnoreNullValue(true);
        copyOptions.setIgnoreProperties("ext20to30", "ext33to36", "ext37to50", "ext52toEnd");
        BeanUtil.copyProperties(editData, dbItem, copyOptions);
        return DbItemReaderUtil.writeItem(list, startByte);
    }

    private byte[] buildEmptyByte(byte[] data) {
        if(data.length == 0){
            return data;
        }
        List<GameItemVo> list = DbItemReaderUtil.readerItem(data);
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byte[] startByte = new byte[4];
        byteBuffer.get(startByte);
        list.forEach(gameItemVo -> {
            gameItemVo.setSealType(0);
            gameItemVo.setItemType(0);
            gameItemVo.setItemId(0);
            gameItemVo.setQuality(0);
            gameItemVo.setDurability(0);
            gameItemVo.setEnchantment(0);
            gameItemVo.setIncreaseType(0);
            gameItemVo.setIncreaseLevel(0);
            gameItemVo.setForgeLevel(0);
            byte[] ext20to30 = gameItemVo.getExt20to30();
            Arrays.fill(ext20to30, (byte) 0);
            gameItemVo.setExt20to30(ext20to30);
            byte[] ext33to36 = gameItemVo.getExt33to36();
            Arrays.fill(ext33to36, (byte) 0);
            gameItemVo.setExt33to36(ext33to36);
            byte[] ext37to50 = gameItemVo.getExt37to50();
            Arrays.fill(ext37to50, (byte) 0);
            gameItemVo.setExt37to50(ext37to50);
            byte[] ext52toEn = gameItemVo.getExt52toEnd();
            Arrays.fill(ext52toEn, (byte) 0);
            gameItemVo.setExt52toEnd(ext52toEn);
        });
        return DbItemReaderUtil.writeItem(list, startByte);
    }

}
