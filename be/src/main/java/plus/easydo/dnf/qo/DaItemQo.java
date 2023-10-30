package plus.easydo.dnf.qo;

import com.mybatisflex.annotation.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


/**
 * 物品缓存 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Table(value = "da_item")
public class DaItemQo extends PageQo{


    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 稀有度
     */
    private String rarity;

}
