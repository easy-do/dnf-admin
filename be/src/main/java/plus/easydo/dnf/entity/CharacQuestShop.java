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
@Table(value = "charac_quest_shop", dataSource = "taiwan_cain")
public class CharacQuestShop {

    @Id(keyType = KeyType.Auto)
    private Long characNo;

    @Column(value = "qp")
    private Integer qp;

    @Column(value = "max_hp")
    private Integer maxHp;

    @Column(value = "max_mp")
    private Integer maxMp;

    @Column(value = "psy_attack")
    private Integer psyAttack;

    @Column(value = "psy_defense")
    private Integer psyDefense;

    @Column(value = "mag_attack")
    private Integer magAttack;

    @Column(value = "mag_defence")
    private Integer magDefence;

    @Column(value = "move_speed")
    private Integer moveSpeed;

    @Column(value = "attack_speed")
    private Integer attackSpeed;

    @Column(value = "hp_regen")
    private Integer hpRegen;

    @Column(value = "mp_regen")
    private Integer mpRegen;

    @Column(value = "all_element_resist")
    private Integer allElementResist;

    @Column(value = "fire_element_resist")
    private Integer fireElementResist;

    @Column(value = "water_element_resist")
    private Integer waterElementResist;

    @Column(value = "light_element_resist")
    private Integer lightElementResist;

    @Column(value = "dark_element_resist")
    private Integer darkElementResist;

    @Column(value = "all_element_attack")
    private Integer allElementAttack;

    @Column(value = "fire_element_attack")
    private Integer fireElementAttack;

    @Column(value = "water_element_attack")
    private Integer waterElementAttack;

    @Column(value = "light_element_attack")
    private Integer lightElementAttack;

    @Column(value = "dark_element_attack")
    private Integer darkElementAttack;

    @Column(value = "psy_critical")
    private Integer psyCritical;

    @Column(value = "mag_critical")
    private Integer magCritical;

    @Column(value = "good_hit")
    private Integer goodHit;

    @Column(value = "evasion")
    private Integer evasion;

    @Column(value = "hit_recovery")
    private Integer hitRecovery;

    @Column(value = "init_count")
    private Integer initCount;

    @Column(value = "separate_psy_mag_attack")
    private Integer separatePsyMagAttack;

    @Column(value = "quest_piece")
    private Integer questPiece;


}
