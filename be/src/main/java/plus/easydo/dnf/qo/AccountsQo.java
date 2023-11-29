package plus.easydo.dnf.qo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


/**
 *  实体类。
 *
 * @author yuzhanfeng
 * @since 2023-10-14
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class AccountsQo extends PageQo {

    private Long uid;

    private String accountname;

    private String qq;

    private Integer billing;

    private String vip;

}
