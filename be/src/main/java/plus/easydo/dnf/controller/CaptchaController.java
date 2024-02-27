package plus.easydo.dnf.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.service.CaptchaService;
import plus.easydo.dnf.vo.CaptchaVo;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.io.IOException;

/**
 * @author yuzhanfeng
 * @Date 2024-01-05 16:36
 * @Description 验证码相关
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/captcha")
public class CaptchaController {

    private final CaptchaService captchaService;

    @GetMapping("/v1")
    public void generateCaptchaV1(@RequestParam(name = "key", required = false)String key,HttpServletResponse response) throws IOException {
        captchaService.generateCaptchaV1(key,response);
    }
    @GetMapping("/v2")
    public R<CaptchaVo> generateCaptchaV2(@RequestParam(name = "key", required = false)String key) {
        return DataResult.ok(captchaService.generateCaptchaV2(key));
    }
}
