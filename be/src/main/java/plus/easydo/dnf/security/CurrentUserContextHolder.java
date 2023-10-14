package plus.easydo.dnf.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.exception.AuthException;

import java.util.Objects;
import java.util.Set;

/**
 * @author laoyu
 * @version 1.0
 * @description 当前用户上下文
 * @date 2023/10/14
 */

public class CurrentUserContextHolder {

    public static Accounts getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Accounts accounts = auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated() ? (Accounts) auth.getDetails() : null;
        if(Objects.isNull(accounts)){
            throw new AuthException("授权无效或过期");
        }
        return accounts;
    }

    public static Set<SimpleGrantedAuthority> getAuthorities() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated() ? (Set)auth.getAuthorities() : null;
    }
}
