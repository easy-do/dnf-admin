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
@Table(value = "limit_create_character")
public class LimitCreateCharacter {

    @Id(keyType = KeyType.Auto)
    private Long mId;

    @Column(value = "count")
    private Integer count;

    @Column(value = "last_access_time")
    private java.util.Date lastAccessTime;

}
