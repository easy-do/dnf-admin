package plus.easydo.dnf.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laoyu
 * @version 1.0
 * @description CurrentUser
 * @date 2023/11/16
 */

@NoArgsConstructor
@Data
public class CurrentUser {

    private Integer uid;
    private String accountname;
    private String qq;
    private Integer billing;
    private String vip;
    private Boolean isAdmin;
}
