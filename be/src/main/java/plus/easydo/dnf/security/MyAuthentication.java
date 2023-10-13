package plus.easydo.dnf.security;

import cn.hutool.core.collection.ListUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.exception.BaseException;

import java.util.Collection;

/**
 * @author laoyu
 * @version 1.0
 * @description 认证信息
 * @date 2023/10/14
 */

public class MyAuthentication implements Authentication {

    private boolean authenticated = true;
    private Accounts accounts;

    private String credentials;

    MyAuthentication(Accounts accounts,String token) {
        this.accounts=accounts;
        this.credentials=token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ListUtil.empty();
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getDetails() {
        return accounts;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return accounts.getAccountname();
    }
}
