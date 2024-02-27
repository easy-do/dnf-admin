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
@Table(value = "skill", dataSource = "taiwan_cain_2nd")
public class Skill {

    @Id(keyType = KeyType.Auto)
    private Long characNo;

    @Column(value = "remain_sp")
    private Integer remainSp;

    @Column(value = "skill_slot")
    private Byte[] skillSlot;

    @Column(value = "request_sp")
    private Byte[] requestSp;

    @Column(value = "sp_garbage")
    private Integer spGarbage;

    @Column(value = "used_sp")
    private Integer usedSp;

    @Column(value = "remain_sp_2nd")
    private Integer remainSp2nd;

    @Column(value = "skill_slot_2nd")
    private Byte[] skillSlot2nd;

    @Column(value = "request_sp_2nd")
    private Byte[] requestSp2nd;

    @Column(value = "skill_slot_lethe")
    private Byte[] skillSlotLethe;

    @Column(value = "lethe_flag")
    private Integer letheFlag;

    @Column(value = "skill_slot_lethe_2nd")
    private Byte[] skillSlotLethe2nd;

    @Column(value = "lethe_flag_2nd")
    private Integer letheFlag2nd;

    @Column(value = "remain_sfp_2nd")
    private Integer remainSfp2nd;

    @Column(value = "remain_sfp_1st")
    private Integer remainSfp1st;

    @Column(value = "skill_command")
    private Byte[] skillCommand;

    @Column(value = "script_version")
    private Integer scriptVersion;


}
