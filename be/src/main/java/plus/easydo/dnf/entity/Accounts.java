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
@Table(value = "accounts", dataSource = "d_taiwan")
public class Accounts implements Serializable {

    @Id(keyType = KeyType.Auto)
    private Integer uid;

    private String accountname;

    private String password;

    private String qq;

    private Integer dzuid;

    private Integer billing;

    private String vip;

    @Column(ignore = true)
    private boolean isAdmin = false;

}
