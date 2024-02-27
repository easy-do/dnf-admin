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
@Table(value = "member_dungeon", dataSource = "taiwan_cain")
public class MemberDungeon {

    @Id(keyType = KeyType.Auto)
    private Long mId;

    @Column(value = "dungeon")
    private String dungeon;


}
