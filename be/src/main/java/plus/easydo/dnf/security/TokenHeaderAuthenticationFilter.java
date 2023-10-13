package plus.easydo.dnf.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.manager.CacheManager;

import java.io.IOException;


/**
 * @author laoyu
 * @version 1.0
 * @description 安全过滤器
 * @date 2023/10/14
 */

public class TokenHeaderAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("Authorization");
        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Accounts accounts = CacheManager.getCurrentUser(token);
            Authentication authentication = new MyAuthentication(accounts,token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
