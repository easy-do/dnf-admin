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
@Table(value = "event_1306_account_reward", dataSource = "taiwan_game_event")
public class Event1306AccountReward {

    @Id(keyType = KeyType.Auto)
    private Integer mId;

    @Column(value = "charac_no")
    private Integer characNo;

    @Column(value = "occ_date")
    private java.util.Date occDate;


}
