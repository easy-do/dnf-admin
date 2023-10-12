package plus.easydo.dnf.dto;

import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description 登录参数封装
 * @date 2023/10/11
 */
@Data
public class LoginDto {

    private String userName;

    private String password;

    private String verificationCode;

}
