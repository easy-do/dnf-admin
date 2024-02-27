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
@Table(value = "charac_inven_expand" , dataSource = "taiwan_cain_2nd")
public class CharacInvenExpand {

    @Id(keyType = KeyType.Auto)
    private Long characNo;

    @Column(value = "cargo")
    private byte[] cargo;

    @Column(value = "cargo_capacity")
    private Integer cargoCapacity;

    @Column(value = "jewel")
    private Byte[] jewel;

    @Column(value = "current_equipslot")
    private String currentEquipslot;

    @Column(value = "switch_equipslot")
    private Byte[] switchEquipslot;

    @Column(value = "expand_equipslot")
    private Byte[] expandEquipslot;

    @Column(value = "redeem_info")
    private Byte[] redeemInfo;


}
