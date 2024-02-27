package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@Table(value = "cash_cera_point", dataSource = "taiwan_billing")
public class CashCeraPoint {

    @Id(keyType = KeyType.Auto)
    private Long account;

    @Column(value = "cera_point")
    private Long ceraPoint;

    @Column(value = "reg_date")
    private LocalDateTime regDate;

    @Column(value = "mod_date")
    private LocalDateTime modDate;

}
