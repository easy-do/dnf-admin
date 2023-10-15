package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  邮件。
 *
 * @author yuzhanfeng
 * @since 2023-10-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "letter", dataSource = "taiwan_cain_2nd")
public class Letter implements Serializable {

    @Id(keyType = KeyType.Auto)
    private Integer letterId;
     /**角色id*/
    private Integer characNo;
    /**发送的角色id*/
    private Integer sendCharacNo = 1;
    /**发的角色名称*/
    private String sendCharacName = "admin";

    private String letterText;

    private LocalDateTime regDate;

    private Integer stat;

}
