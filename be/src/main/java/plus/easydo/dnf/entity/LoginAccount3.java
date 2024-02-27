package plus.easydo.dnf.entity;

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
@Table(value = "login_account_3", dataSource = "taiwan_login")
public class LoginAccount3 {

    @Id(keyType = KeyType.Auto)
    private Integer mId;

    @Column(value = "m_channel_no")
    private Integer mChannelNo;

    @Column(value = "login_status")
    private Integer loginStatus;

    @Column(value = "last_login_date")
    private java.util.Date lastLoginDate;

    @Column(value = "login_ip")
    private String loginIp;


}
