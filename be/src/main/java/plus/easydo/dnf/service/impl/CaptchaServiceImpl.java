package plus.easydo.dnf.service.impl;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.constant.SystemConstant;
import plus.easydo.dnf.service.CaptchaService;
import plus.easydo.dnf.vo.CaptchaVo;

import java.io.IOException;
import java.util.Objects;

/**
 * @author yuzhanfeng
 * @Date 2024-01-05 16:46
 * @Description 验证码
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    private static final TimedCache<String, LineCaptcha> CACHE = CacheUtil.newTimedCache(60000);

    @Override
    public void generateCaptchaV1(String captchaKey, HttpServletResponse response) throws IOException {
        if(CharSequenceUtil.isNotBlank(captchaKey)){
            CACHE.remove(captchaKey);
        }
        captchaKey = UUID.fastUUID().toString(true);
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(100, 50, 4, 50);
        //缓存一份
        CACHE.put(captchaKey,captcha);
        Cookie cookie = new Cookie(SystemConstant.CAPTCHA_KEY,captchaKey);
        response.addCookie(cookie);
        ServletOutputStream opt = response.getOutputStream();
        captcha.write(opt);
        IoUtil.close(opt);
    }

    @Override
    public CaptchaVo generateCaptchaV2(String captchaKey) {
        if(CharSequenceUtil.isNotBlank(captchaKey)){
            CACHE.remove(captchaKey);
        }
        captchaKey = UUID.fastUUID().toString(true);
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(100, 50, 4, 50);
        //缓存一份
        CACHE.put(captchaKey,captcha);
        CaptchaVo captchaVo = new CaptchaVo();
        captchaVo.setKey(captchaKey);
        captchaVo.setImg(captcha.getImageBase64Data());
        return captchaVo;
    }

    @Override
    public boolean verify(String key,String captchaCode) {
        LineCaptcha captcha = CACHE.get(key);
        if(Objects.isNull(captcha)){
            return false;
        }
        boolean result = captcha.verify(captchaCode);
        if(result){
            CACHE.remove(key);
        }
        return result;
    }
}
