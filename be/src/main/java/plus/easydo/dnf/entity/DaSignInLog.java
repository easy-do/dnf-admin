package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *  实体类。
 *
 * @author yuzhanfeng
 * @since 2023-10-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "da_sign_in_log", dataSource = "d_taiwan")
public class DaSignInLog implements Serializable {

    @Id(keyType = KeyType.Auto)
    private Long id;

    private Long configId;

    private String dataJson;

    private Integer signInRoleId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
