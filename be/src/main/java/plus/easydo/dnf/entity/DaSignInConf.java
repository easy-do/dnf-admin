package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
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
@Table(value = "da_sign_in_conf")
public class DaSignInConf implements Serializable {

    @Id(keyType = KeyType.Auto)
    private Long id;

    private String configName;

    private Date configDate;

    private String configJson;

    private String remark;

    private Integer createId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @Column(ignore = true)
    private LocalDateTime signInTime;

}
