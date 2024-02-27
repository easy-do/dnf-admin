package plus.easydo.dnf.entity;

import lombok.Data;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

/**
 * 角色时装。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Table(value = "user_items", dataSource = "taiwan_cain_2nd")
public class UserItems {

    @Id(keyType = KeyType.Auto)
    private Long uiId;

    @Column(value = "charac_no")
    private Long characNo;

    @Column(value = "slot")
    private Integer slot;

    @Column(value = "it_id")
    private Long itId;

    @Column(value = "expire_date")
    private java.util.Date expireDate;

    @Column(value = "obtain_from")
    private Integer obtainFrom;

    @Column(value = "reg_date")
    private java.util.Date regDate;

    @Column(value = "ipg_agency_no")
    private String ipgAgencyNo;

    @Column(value = "ability_no")
    private Integer abilityNo;

    @Column(value = "stat")
    private Integer stat;

    @Column(value = "clear_avatar_id")
    private Integer clearAvatarId;

    @Column(value = "jewel_socket")
    private Byte[] jewelSocket;

    @Column(value = "item_lock_key")
    private Integer itemLockKey;

    @Column(value = "to_ipg_agency_no")
    private String toIpgAgencyNo;

    @Column(value = "m_time")
    private java.util.Date mTime;

    @Column(value = "hidden_option")
    private Integer hiddenOption;

    @Column(value = "emblem_endurance")
    private Integer emblemEndurance;

    @Column(value = "color1")
    private Integer color1;

    @Column(value = "color2")
    private Integer color2;

    @Column(value = "trade_restrict")
    private Integer tradeRestrict;

    @Column(ignore = true)
    private String itemName;

}
