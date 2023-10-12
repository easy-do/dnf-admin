package plus.easydo.dnf.vo;

import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description 登录返回值
 * @date 2023/10/11
 */
@Data
public class LoginResultVo {

    private String userName;

    private String token;
}
