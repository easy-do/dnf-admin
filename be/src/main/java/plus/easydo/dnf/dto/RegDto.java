package plus.easydo.dnf.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description 登录参数封装
 * @date 2023/10/11
 */
@Data
public class RegDto {

    @NotBlank(message = "账号不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String verificationCode;

}
