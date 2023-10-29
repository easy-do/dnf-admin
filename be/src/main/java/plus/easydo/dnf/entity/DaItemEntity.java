package plus.easydo.dnf.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;


/**
 * 物品缓存 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Table(value = "da_item")
public class DaItemEntity {

    /**
     * 装备id
     */
    @Id(keyType = KeyType.None)
    @Column(value = "id")
    @ExcelProperty(value = "物品id")
    private Long id;

    /**
     * 名称
     */
    @Column(value = "name")
    @ExcelProperty(value = "物品名称")
    private String name;

    /**
     * 类型
     */
    @ExcelProperty(value = "物品类型")
    @Column(value = "type")
    private String type;

    /**
     * 稀有度
     */
    @ExcelProperty(value = "稀有度")
    @Column(value = "rarity")
    private String rarity;

}
