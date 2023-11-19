package plus.easydo.dnf.vo;

import cn.hutool.core.lang.tree.Tree;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<Object> menu;
    private List<String> role;
    private List<String> resource;
}
