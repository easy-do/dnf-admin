package plus.easydo.dnf.vo;

import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description 验证码返回值
 * @date 2024/1/6
 */
@Data
public class CaptchaVo {

    private String key;

    private String img;

}
