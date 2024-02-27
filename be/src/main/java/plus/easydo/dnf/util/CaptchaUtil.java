package plus.easydo.dnf.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import plus.easydo.dnf.constant.SystemConstant;

/**
 * @author laoyu
 * @version 1.0
 * @description 验证码工具
 * @date 2024/1/6
 */

public class CaptchaUtil {

    private CaptchaUtil() {
    }

    public static String getCaptchaKey(HttpServletRequest request){
        Cookie[] cookies = request.getCookies(); // 获取所有的Cookie信息
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if(SystemConstant.CAPTCHA_KEY.equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
