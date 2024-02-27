package plus.easydo.dnf.service.impl;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.entity.DaItemEntity;
import plus.easydo.dnf.enums.HistoryLogTypeEnum;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.qo.DaGameEventQo;
import plus.easydo.dnf.service.DaGameEventService;
import plus.easydo.dnf.entity.DaGameEvent;
import plus.easydo.dnf.mapper.DaGameEventMapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static plus.easydo.dnf.entity.table.DaGameEventTableDef.DA_GAME_EVENT;

/**
 * 游戏事件 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class DaGameEventServiceImpl extends ServiceImpl<DaGameEventMapper, DaGameEvent> implements DaGameEventService {

    private static final List<String> ONE_PARAM_TYPE = HistoryLogTypeEnum.oneParamType();
    private static final List<String> TWO_PARAM_TYPE = HistoryLogTypeEnum.twoParamType();


    @Override
    public Page<DaGameEvent> gameEventPage(DaGameEventQo daGameEventQo) {
        QueryWrapper query = query().from(DA_GAME_EVENT)
                .and(DA_GAME_EVENT.ACCOUNT_ID.eq(daGameEventQo.getAccountId()))
                .and(DA_GAME_EVENT.CHANNEL.eq(daGameEventQo.getChannel()))
                .and(DA_GAME_EVENT.CHARCA_NO.eq(daGameEventQo.getCharcaNo()))
                .and(DA_GAME_EVENT.CHARCA_NAME.like(daGameEventQo.getCharcaName()))
                .and(DA_GAME_EVENT.CHANNEL.eq(daGameEventQo.getChannel()))
                .and(DA_GAME_EVENT.LEVEL.eq(daGameEventQo.getLevel()))
                .and(DA_GAME_EVENT.OPTION_TYPE.eq(daGameEventQo.getOptionType()))
                .and(DA_GAME_EVENT.CLIENT_IP.eq(daGameEventQo.getClientIp()));
        Page<DaGameEvent> res = page(new Page<>(daGameEventQo.getCurrent(), daGameEventQo.getPageSize()), query);
        for (DaGameEvent daGameEvent : res.getRecords()) {
            try {
                String optionType = daGameEvent.getOptionType();
                if(HistoryLogTypeEnum.ITEM_ADD.getType().equals(optionType)){
                    buildItemOptionInfo(daGameEvent,daGameEvent.getParam2(),HistoryLogTypeEnum.ITEM_ADD,daGameEvent.getParam3());
                }
                if(HistoryLogTypeEnum.ITEM_RE.getType().equals(optionType)){
                    buildItemOptionInfo(daGameEvent,daGameEvent.getParam2(),HistoryLogTypeEnum.ITEM_RE,daGameEvent.getParam1());
                }
                if(HistoryLogTypeEnum.CARGO_ADD.getType().equals(optionType)){
                    buildItemOptionInfo(daGameEvent,daGameEvent.getParam1(),HistoryLogTypeEnum.CARGO_ADD,daGameEvent.getParam2());
                }
                if(HistoryLogTypeEnum.CARGO_RE.getType().equals(optionType)){
                    buildItemOptionInfo(daGameEvent,daGameEvent.getParam1(),HistoryLogTypeEnum.CARGO_RE,daGameEvent.getParam2());
                }
                if(HistoryLogTypeEnum.ACCOUNT_CARGO_ADD.getType().equals(optionType)){
                    buildItemOptionInfo(daGameEvent,daGameEvent.getParam2(),HistoryLogTypeEnum.ACCOUNT_CARGO_ADD,daGameEvent.getParam3());
                }
                if(HistoryLogTypeEnum.ACCOUNT_CARGO_RE.getType().equals(optionType)){
                    buildItemOptionInfo(daGameEvent,daGameEvent.getParam2(),HistoryLogTypeEnum.ACCOUNT_CARGO_RE,daGameEvent.getParam3());
                }
                if(HistoryLogTypeEnum.AVATAR_ADD.getType().equals(optionType)){
                    buildItemOptionInfo(daGameEvent,daGameEvent.getParam1(),HistoryLogTypeEnum.AVATAR_ADD,"");
                }
                if(HistoryLogTypeEnum.MONEY_ADD.getType().equals(optionType)){
                    daGameEvent.setOptionInfo(CharSequenceUtil.format(HistoryLogTypeEnum.MONEY_ADD.getOptionInfo(),daGameEvent.getParam2()));
                }
                if(HistoryLogTypeEnum.MONEY_RE.getType().equals(optionType)){
                    daGameEvent.setOptionInfo(CharSequenceUtil.format(HistoryLogTypeEnum.MONEY_RE.getOptionInfo(),daGameEvent.getParam2()));
                }
                if(HistoryLogTypeEnum.LEVEL_UP.getType().equals(optionType)){
                    daGameEvent.setOptionInfo(CharSequenceUtil.format(HistoryLogTypeEnum.LEVEL_UP.getOptionInfo(),daGameEvent.getParam1(),daGameEvent.getParam2()));
                }
                if(HistoryLogTypeEnum.LOGIN.getType().equals(optionType)){
                    daGameEvent.setOptionInfo(HistoryLogTypeEnum.LOGIN.getOptionInfo());
                }
                if(HistoryLogTypeEnum.LOGOUT.getType().equals(optionType)){
                    daGameEvent.setOptionInfo(HistoryLogTypeEnum.LOGOUT.getOptionInfo());
                }
                if(HistoryLogTypeEnum.MAIL_R.getType().equals(optionType)){
                    daGameEvent.setOptionInfo(CharSequenceUtil.format(HistoryLogTypeEnum.MAIL_R.getOptionInfo(),daGameEvent.getParam1()));
                }
                if(HistoryLogTypeEnum.KILL_MOB.getType().equals(optionType)){
                    daGameEvent.setOptionInfo(CharSequenceUtil.format(HistoryLogTypeEnum.KILL_MOB.getOptionInfo(),daGameEvent.getParam1()));
                }
                if(HistoryLogTypeEnum.DUNGEON_LEAVE.getType().equals(optionType)){
                    daGameEvent.setOptionInfo(CharSequenceUtil.format(HistoryLogTypeEnum.DUNGEON_LEAVE.getOptionInfo(),daGameEvent.getParam1()));
                }
                if(HistoryLogTypeEnum.DUNGEON_ENTER.getType().equals(optionType)){
                    daGameEvent.setOptionInfo(CharSequenceUtil.format(HistoryLogTypeEnum.DUNGEON_ENTER.getOptionInfo(),daGameEvent.getParam1()));
                }
                if(HistoryLogTypeEnum.MOVE_AREA.getType().equals(optionType)){
                    daGameEvent.setOptionInfo(CharSequenceUtil.format(HistoryLogTypeEnum.MOVE_AREA.getOptionInfo(),daGameEvent.getParam1(),daGameEvent.getParam2()));
                }
                if(HistoryLogTypeEnum.BUY_CASH_ITEM.getType().equals(optionType)){
                    buildItemOptionInfo(daGameEvent,daGameEvent.getParam2(),HistoryLogTypeEnum.MONEY_RE,daGameEvent.getParam3());
                }
                if(HistoryLogTypeEnum.RANDOM_OPTION_ITEM.getType().equals(optionType)){
                    daGameEvent.setOptionInfo(CharSequenceUtil.format(HistoryLogTypeEnum.RANDOM_OPTION_ITEM.getOptionInfo(),daGameEvent.getParam1()));
                }
                if(HistoryLogTypeEnum.UPGRADE_SUCCESS.getType().equals(optionType)){
                    buildItemOptionInfo(daGameEvent,daGameEvent.getParam1(),HistoryLogTypeEnum.UPGRADE_SUCCESS,daGameEvent.getParam2(),daGameEvent.getParam3());
                }
                if(HistoryLogTypeEnum.UPGRADE_FAILED.getType().equals(optionType)){
                    buildItemOptionInfo(daGameEvent,daGameEvent.getParam1(),HistoryLogTypeEnum.UPGRADE_FAILED,daGameEvent.getParam2(),daGameEvent.getParam3());
                }
                if(HistoryLogTypeEnum.APPLY_ITEM.getType().equals(optionType)){
                    buildItemOptionInfo(daGameEvent,daGameEvent.getParam2(),HistoryLogTypeEnum.APPLY_ITEM,daGameEvent.getParam1());
                }
                if(HistoryLogTypeEnum.QUEST_COMPLETE.getType().equals(optionType)){
                    daGameEvent.setOptionInfo(CharSequenceUtil.format(HistoryLogTypeEnum.QUEST_COMPLETE.getOptionInfo(),daGameEvent.getParam1()));
                }
//                if (ONE_PARAM_TYPE.contains(optionType)) {
//                    DaItemEntity daItem = CacheManager.ITEM_INFO_CACHE.get(Long.valueOf(daGameEvent.getParam1()));
//                    if (Objects.nonNull(daItem)) {
//                        daGameEvent.setParam1(daItem.getName());
//                    }
//                }
//                if (TWO_PARAM_TYPE.contains(daGameEvent.getOptionType())) {
//                    DaItemEntity daItem = CacheManager.ITEM_INFO_CACHE.get(Long.valueOf(daGameEvent.getParam2()));
//                    if (Objects.nonNull(daItem)) {
//                        daGameEvent.setParam2(daItem.getName());
//                    }
//                }
            } catch (Exception e) {
                // do nothing
            }
        }
        return res;
    }

    /**
     * 功能描述
     *
     * @param daGameEvent daGameEvent
     * @param itemId 物品id
     * @param historyLogTypeEnum historyLogTypeEnum 操作类型
     * @param extParam1 其他参数
     * @author laoyu
     * @date 2024/2/4
     */
    private void buildItemOptionInfo(DaGameEvent daGameEvent, String itemId, HistoryLogTypeEnum historyLogTypeEnum,String extParam1){
        DaItemEntity daItem = CacheManager.ITEM_INFO_CACHE.get(Long.valueOf(itemId));
        if (Objects.nonNull(daItem)) {
            daGameEvent.setOptionInfo(CharSequenceUtil.format(historyLogTypeEnum.getOptionInfo(),daItem.getName(),extParam1));
        }else {
            daGameEvent.setOptionInfo(CharSequenceUtil.format(historyLogTypeEnum.getOptionInfo(),itemId,extParam1));
        }
    }
    /**
     * 功能描述
     *
     * @param daGameEvent daGameEvent
     * @param itemId 物品id
     * @param historyLogTypeEnum historyLogTypeEnum 操作类型
     * @param extParam1 其他参数
     * @author laoyu
     * @date 2024/2/4
     */
    private void buildItemOptionInfo(DaGameEvent daGameEvent, String itemId, HistoryLogTypeEnum historyLogTypeEnum,String extParam1,String extParam2){
        DaItemEntity daItem = CacheManager.ITEM_INFO_CACHE.get(Long.valueOf(itemId));
        if (Objects.nonNull(daItem)) {
            daGameEvent.setOptionInfo(CharSequenceUtil.format(historyLogTypeEnum.getOptionInfo(),daItem.getName(),extParam1,extParam2));
        }else {
            daGameEvent.setOptionInfo(CharSequenceUtil.format(historyLogTypeEnum.getOptionInfo(),itemId,extParam1,extParam2));
        }
    }

    @Override
    public boolean removeAll() {
        QueryWrapper query = query().from(DA_GAME_EVENT).and("1=1");
        return remove(query);
    }

    @Override
    public Map<String,Integer> getMaxFileIndex() {
        String sql = "SELECT file_name, MAX(file_index) as file_index FROM d_taiwan.da_game_event GROUP BY file_name";
        List<Row> rows = Db.selectListBySql(sql);
        Map<String,Integer> result = MapUtil.newHashMap(rows.size());
        rows.forEach(row -> result.put((String) row.get("file_name"), (Integer) row.get("file_index")));
        return result;
    }
}
