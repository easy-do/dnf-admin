package plus.easydo.dnf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yuzhanfeng
 * @Date 2024-01-12 16:51
 * @Description 拦截器注册
 */
@Configuration
public class WebInterceptorConfig implements WebMvcConfigurer {


    @Autowired
    private LicenseInterceptor licenseInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(licenseInterceptor).addPathPatterns("/api/**");
    }
}
