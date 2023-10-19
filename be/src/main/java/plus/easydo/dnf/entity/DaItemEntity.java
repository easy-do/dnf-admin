package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;

import java.lang.Long;
import java.lang.String;

/**
 * 物品缓存 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Table(value = "da_item")
public class DaItemEntity {

    /**
     * 装备id
     */
    @Column(value = "id")
    private Long id;

    /**
     * 名称
     */
    @Column(value = "name")
    private String name;

    /**
     * 类型
     */
    @Column(value = "type")
    private String type;

    /**
     * 稀有度
     */
    @Column(value = "rarity")
    private String rarity;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }
}
