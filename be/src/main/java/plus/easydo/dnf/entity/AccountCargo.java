package plus.easydo.dnf.entity;

import lombok.Builder;
import lombok.Data;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import java.time.LocalDateTime;

/**
 * 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@Table(value = "account_cargo", dataSource = "taiwan_cain")
public class AccountCargo {

    @Id(keyType = KeyType.Auto)
    private Long mId;

    @Column(value = "money")
    private Integer money;

    @Column(value = "capacity")
    private Integer capacity;

    @Column(value = "cargo")
    private byte[] cargo;

    @Column(value = "occ_time")
    private LocalDateTime occTime;


}
