package plus.easydo.dnf.config;

import cn.hutool.core.exceptions.ExceptionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import plus.easydo.dnf.exception.LicenseException;
import plus.easydo.dnf.service.LicenseService;

import java.util.Arrays;
import java.util.List;

/**
 * @author yuzhanfeng
 * @Date 2024-01-12 16:40
 * @Description 许可拦截
 */
@Slf4j
@Component
public class LicenseInterceptor implements HandlerInterceptor {

    @Autowired
    private LicenseService licenseService;

    List<String> skipPath = Arrays.asList("LicenseController","UserController","CaptchaController");

    volatile boolean checkSuccess = false;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //类名
        String name = ((HandlerMethod)handler).getBean().getClass().getName();
        //方法名
//        String method = ((HandlerMethod)handler).getMethod().getName();
        if(checkSuccess || skipPath.contains(name)){
            return true;
        }
        try {
            licenseService.check();
            checkSuccess = true;
        }catch (Exception e){
            throw new LicenseException(ExceptionUtil.getMessage(e));
        }
        return checkSuccess;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        // do nothing
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception ex) throws Exception {
        // do nothing
    }
}
