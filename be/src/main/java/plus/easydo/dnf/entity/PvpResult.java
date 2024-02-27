package plus.easydo.dnf.entity;

import lombok.Builder;
import lombok.Data;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

/**
 * 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@Table(value = "pvp_result", dataSource = "taiwan_cain")
public class PvpResult {

    @Id(keyType = KeyType.Auto)
    private Long characNo;

    /**胜利场次*/
    @Column(value = "win")
    private Integer win;

    @Column(value = "lose")
    private Integer lose;

    /**胜点*/
    @Column(value = "pvp_point")
    private Integer pvpPoint;

    /**段位*/
    @Column(value = "pvp_grade")
    private Integer pvpGrade;

    @Column(value = "pvp_grade_ext")
    private Integer pvpGradeExt;

    @Column(value = "avg_kill_count")
    private Integer avgKillCount;

    @Column(value = "avg_buf_count")
    private Integer avgBufCount;

    @Column(value = "avg_debuf_count")
    private Integer avgDebufCount;

    @Column(value = "avg_heal_count")
    private Integer avgHealCount;

    @Column(value = "avg_counter_count")
    private Integer avgCounterCount;

    @Column(value = "avg_back_atk_count")
    private Integer avgBackAtkCount;

    @Column(value = "avg_union_hit_count")
    private Integer avgUnionHitCount;

    @Column(value = "avg_overkill_count")
    private Integer avgOverkillCount;

    @Column(value = "avg_aerial_count")
    private Integer avgAerialCount;

    @Column(value = "avg_combo_count")
    private Integer avgComboCount;

    @Column(value = "avg_attacked_count")
    private Integer avgAttackedCount;

    @Column(value = "avg_deal_damage")
    private Integer avgDealDamage;

    @Column(value = "avg_technic")
    private Integer avgTechnic;

    @Column(value = "avg_style")
    private Integer avgStyle;

    @Column(value = "avg_hit_penalty")
    private Integer avgHitPenalty;

    @Column(value = "pvp_count")
    private Integer pvpCount;

    @Column(value = "win_point")
    private Integer winPoint;

    @Column(value = "last_play_time")
    private java.util.Date lastPlayTime;

    @Column(value = "play_count")
    private Integer playCount;

    @Column(value = "play_time")
    private Integer playTime;

    @Column(value = "pvp_grade_ext_update_time")
    private java.util.Date pvpGradeExtUpdateTime;


}
