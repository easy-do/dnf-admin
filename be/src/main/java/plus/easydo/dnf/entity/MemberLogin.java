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
@Table(value = "member_login", dataSource = "taiwan_login")
public class MemberLogin {

    @Id(keyType = KeyType.Auto)
    private Long mId;

    @Column(value = "login_time")
    private Integer loginTime;

    @Column(value = "expire_time")
    private Integer expireTime;

    @Column(value = "last_play_time")
    private Integer lastPlayTime;

    @Column(value = "total_account_fail")
    private Integer totalAccountFail;

    @Column(value = "account_fail")
    private Integer accountFail;

    @Column(value = "report_cnt")
    private Integer reportCnt;

    @Column(value = "reliable_flag")
    private Integer reliableFlag;

    @Column(value = "trade_gold_daily")
    private Integer tradeGoldDaily;

    @Column(value = "last_gift_time")
    private Integer lastGiftTime;

    @Column(value = "gift_cnt")
    private Integer giftCnt;

    @Column(value = "login_ip")
    private String loginIp;

    @Column(value = "security_flag")
    private Integer securityFlag;

    @Column(value = "power_side")
    private Integer powerSide;

    @Column(value = "dungeon_gain_gold")
    private Integer dungeonGainGold;

    @Column(value = "school_id")
    private Integer schoolId;

    @Column(value = "rating")
    private Float rating;

    @Column(value = "cleanpad_point")
    private Integer cleanpadPoint;

    @Column(value = "tutorial_skipable")
    private String tutorialSkipable;

    @Column(value = "event_charac_flag")
    private Integer eventCharacFlag;

    @Column(value = "garena_token_key")
    private Long garenaTokenKey;
}
