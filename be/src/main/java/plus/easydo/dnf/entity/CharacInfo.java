package plus.easydo.dnf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import plus.easydo.dnf.config.MyInsertListener;
import plus.easydo.dnf.config.MySetListener;
import plus.easydo.dnf.config.MyUpdateListener;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *  角色信息。
 *
 * @author yuzhanfeng
 * @since 2023-10-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "charac_info",  dataSource = "taiwan_cain" ,onSet = MySetListener.class, onInsert = MyInsertListener.class, onUpdate = MyUpdateListener.class)
public class CharacInfo implements Serializable {

    private Long mId;

    @Id(keyType = KeyType.Auto)
    private Long characNo;

    private String characName;

    private Integer village;

    private Integer job;

    private Integer lev;

    private Integer exp;

    private Integer growType;

    private Integer hp;

    @Column(value = "maxHP")
    private Integer maxHP;

    @Column(value = "maxMP")
    private Integer maxMP;

    private Integer phyAttack;

    private Integer phyDefense;

    private Integer magAttack;

    private Integer magDefense;

    private byte[] elementResist;

    private byte[] specProperty;

    private Integer invenWeight;

    private Integer hpRegen;

    private Integer mpRegen;

    private Integer moveSpeed;

    private Integer attackSpeed;

    private Integer castSpeed;

    private Integer hitRecovery;

    private Integer jump;

    private Integer characWeight;

    private Integer fatigue;

    private Integer maxFatigue;

    private Integer premiumFatigue;

    private Integer maxPremiumFatigue;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastPlayTime;

    private Integer dungeonClearPoint;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deleteTime;

    private Integer deleteFlag;

    private Long guildId;

    private Integer guildRight;

    private Integer memberFlag;

    private Integer sex;

    private Integer expertJob;

    private Integer skillTreeIndex;

    private Long linkCharacNo;

    private Integer eventCharacLevel;

    private Integer guildSecede;

    private Integer startTime;

    private Integer finishTime;

    private Integer competitionArea;

    private Integer competitionPeriod;

    private Integer mercenaryStartTime;

    private Integer mercenaryFinishTime;

    private Integer mercenaryArea;

    private Integer mercenaryPeriod;

    private String vip;

    @Column(ignore = true)
    private String jobName;

    @Column(ignore = true)
    private String expertJobName;

}
