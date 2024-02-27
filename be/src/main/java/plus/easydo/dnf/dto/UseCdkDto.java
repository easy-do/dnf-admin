package plus.easydo.dnf.dto;

import lombok.Data;

import java.util.List;

/**
 * @author yuzhanfeng
 * @Date 2024-01-05 14:26
 * @Description 兑换cdk参数封装
 */
@Data
public class UseCdkDto {

    private Long characNo;

    private List<String> cdks;

    private String verificationCode;

    private String captchaKey;
}
