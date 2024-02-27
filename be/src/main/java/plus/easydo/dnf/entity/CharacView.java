package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;


/**
 * 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Table(value = "charac_view", dataSource = "taiwan_cain")
public class CharacView {

    @Id(keyType = KeyType.Auto)
    private Long mId;

    @Column(value = "info")
    private Byte[] info;

    @Column(value = "slot_effect_count")
    private Integer slotEffectCount;

    @Column(value = "charac_slot_limit")
    private Integer characSlotLimit;

    @Column(value = "hash_key")
    private String hashKey;

    @Column(value = "charac_count")
    private Integer characCount;

}
