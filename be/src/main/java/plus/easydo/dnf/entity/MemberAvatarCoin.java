package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Builder;
import lombok.Data;


/**
 * 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@Table(value = "member_avatar_coin", dataSource = "taiwan_cain_2nd")
public class MemberAvatarCoin {

    @Id(keyType = KeyType.Auto)
    private Long mId;

    @Column(value = "avatar_coin")
    private Integer avatarCoin;
}
