package plus.easydo.dnf.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import plus.easydo.dnf.entity.Accounts;

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
        return auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated() ? (Accounts)auth.getDetails() : null;
    }

    public static Set<SimpleGrantedAuthority> getAuthorities() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated() ? (Set)auth.getAuthorities() : null;
    }
}
