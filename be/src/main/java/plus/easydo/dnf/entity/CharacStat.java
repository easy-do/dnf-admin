package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Builder;
import lombok.Data;

/**
 * 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@Table(value = "charac_stat",dataSource = "taiwan_cain")
public class CharacStat {

    @Id(keyType = KeyType.Auto)
    private Long characNo;

    @Column(value = "village")
    private Integer village;

    @Column(value = "exp")
    private Integer exp;

    @Column(value = "HP")
    private Integer hp;

    @Column(value = "fatigue")
    private Integer fatigue;

    @Column(value = "used_fatigue")
    private Integer usedFatigue;

    @Column(value = "premium_fatigue")
    private Integer premiumFatigue;

    @Column(value = "dungeon_clear_point")
    private Integer dungeonClearPoint;

    @Column(value = "last_play_time")
    private java.util.Date lastPlayTime;

    @Column(value = "forbidden_to_play")
    private String forbiddenToPlay;

    @Column(value = "forbidden_due_to")
    private java.util.Date forbiddenDueTo;

    @Column(value = "tutorial_flag")
    private Integer tutorialFlag;

    @Column(value = "trade_gold_total")
    private Integer tradeGoldTotal;

    @Column(value = "trade_gold_total_billion")
    private Integer tradeGoldTotalBillion;

    @Column(value = "trade_gold_daily")
    private Integer tradeGoldDaily;

    @Column(value = "dungeon_map_pass_cnt")
    private Integer dungeonMapPassCnt;

    @Column(value = "dungeon_map_help_pass_cnt")
    private Integer dungeonMapHelpPassCnt;

    @Column(value = "help_abuse_point")
    private Integer helpAbusePoint;

    @Column(value = "chaos_point")
    private Integer chaosPoint;

    @Column(value = "chaos_exp")
    private Integer chaosExp;

    @Column(value = "chaos_mode_count")
    private Integer chaosModeCount;

    @Column(value = "chaos_kill_count")
    private Integer chaosKillCount;

    @Column(value = "chaos_die_count")
    private Integer chaosDieCount;

    @Column(value = "chaos_die_time")
    private java.util.Date chaosDieTime;

    @Column(value = "chaos_kill_time")
    private java.util.Date chaosKillTime;

    @Column(value = "assault_count")
    private Integer assaultCount;

    @Column(value = "luck_point")
    private Integer luckPoint;

    @Column(value = "dungeon_play_count")
    private Integer dungeonPlayCount;

    @Column(value = "help_abuse_ratio")
    private Integer helpAbuseRatio;

    @Column(value = "help_abuse_exp")
    private Integer helpAbuseExp;

    @Column(value = "expert_job_exp")
    private Integer expertJobExp;

    @Column(value = "fatigue_battery_charging")
    private Integer fatigueBatteryCharging;

    @Column(value = "escalade_tutorial_flag")
    private String escaladeTutorialFlag;

    @Column(value = "power_war_point")
    private Integer powerWarPoint;

    @Column(value = "power_war_assault_count")
    private Integer powerWarAssaultCount;

    @Column(value = "power_war_assault_victory_count")
    private Integer powerWarAssaultVictoryCount;

    @Column(value = "fatigue_grownup_buff")
    private Integer fatigueGrownupBuff;

    @Column(value = "village_prev")
    private Integer villagePrev;

    @Column(value = "last_play_time_powerwar")
    private java.util.Date lastPlayTimePowerwar;

    @Column(value = "emotion")
    private Integer emotion;

    @Column(value = "add_slot_flag")
    private Integer addSlotFlag;

    @Column(value = "member_dungeon_flag")
    private Integer memberDungeonFlag;

    @Column(value = "open_flag")
    private Integer openFlag;

    @Column(value = "member_bonus_fatigue")
    private Integer memberBonusFatigue;

    @Column(value = "birthday_effect_time")
    private java.util.Date birthdayEffectTime;

    @Column(value = "visible_flags")
    private Integer visibleFlags;

    @Column(value = "add_equipslot_flag")
    private Integer addEquipslotFlag;

    @Column(value = "channel_equipslot_switch")
    private Integer channelEquipslotSwitch;

    @Column(value = "expand_equipslot_switch")
    private Integer expandEquipslotSwitch;

    @Column(value = "growth_power_reward")
    private Integer growthPowerReward;

    @Column(value = "chaos_respon_time")
    private java.util.Date chaosResponTime;

    @Column(value = "last_play_dungeon_index")
    private Integer lastPlayDungeonIndex;

    @Column(value = "total_play_time")
    private Integer totalPlayTime;

}
