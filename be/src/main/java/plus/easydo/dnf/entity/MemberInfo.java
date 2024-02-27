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
@Table(value = "member_info")
public class MemberInfo {

    @Id(keyType = KeyType.Auto)
    private Long mId;

    @Column(value = "user_id")
    private Long userId;

    @Column(value = "user_name")
    private String userName;

    @Column(value = "first_ssn")
    private String firstSsn;

    @Column(value = "second_ssn")
    private String secondSsn;

    @Column(value = "passwd")
    private String passwd;

    @Column(value = "mobile_no")
    private String mobileNo;

    @Column(value = "reg_date")
    private Integer regDate;

    @Column(value = "email")
    private String email;

    @Column(value = "q_no")
    private Integer qNo;

    @Column(value = "q_answer")
    private String qAnswer;

    @Column(value = "updt_date")
    private java.util.Date updtDate;

    @Column(value = "state")
    private Integer state;

    @Column(value = "nickname")
    private String nickname;

    @Column(value = "email_yn")
    private Object emailYn;

    @Column(value = "ssn_check")
    private Integer ssnCheck;

    @Column(value = "slot")
    private Integer slot;

    @Column(value = "last_play_time")
    private java.util.Date lastPlayTime;

    @Column(value = "hangame_flag")
    private Integer hangameFlag;

    @Column(value = "hanmon_flag")
    private Integer hanmonFlag;

    @Column(value = "m_type")
    private Integer mType;

}
