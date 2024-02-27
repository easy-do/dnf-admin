package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@Table(value = "inventory" , dataSource = "taiwan_cain_2nd")
public class Inventory {

    @Id(keyType = KeyType.Auto)
    private Long characNo;

    @Column(value = "money")
    private Long money;

    @Column(value = "coin")
    private Integer coin;

    @Column(value = "inventory")
    private byte[] inventory;

    @Column(value = "equipslot")
    private byte[] equipslot;

    @Column(value = "pay_coin")
    private Integer payCoin;

    @Column(value = "event_coin")
    private Integer eventCoin;

    @Column(value = "creature")
    private byte[] creature;

    @Column(value = "creature_flag")
    private Integer creatureFlag;

    @Column(value = "katagaki")
    private byte[] katagaki;

    @Column(value = "inventory_capacity")
    private Integer inventoryCapacity;

    @Column(value = "avatar_coin")
    private Integer avatarCoin;


}
