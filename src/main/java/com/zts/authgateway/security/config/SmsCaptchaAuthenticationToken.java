package com.zts.authgateway.security.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author zhangtusheng
 * @Date 2023 03 12 21 05
 * @describe：
 **/
public class SmsCaptchaAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private final String captcha;

    public SmsCaptchaAuthenticationToken(Object principal, String captcha) {
        super(null);
        this.principal = principal;
        this.captcha = captcha;
        setAuthenticated(false);
    }

    public SmsCaptchaAuthenticationToken(Object principal, String captcha,
                                         Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.captcha = captcha;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public String getCaptcha() {
        return captcha;
    }
}

