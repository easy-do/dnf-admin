package plus.easydo.dnf.dto;

import lombok.Data;


/**
 * @author yuzhanfeng
 * @Date 2024-01-05 14:26
 * @Description 注册许可参数封装
 */
@Data
public class RegLicenseDto {

    private String license;

    private String verificationCode;

    private String captchaKey;

}
