package com.zts.authgateway.security.config.provider;

import com.zts.authgateway.security.config.SmsCaptchaAuthenticationToken;
import com.zts.authgateway.security.config.service.CaptchaService;
import com.zts.authgateway.security.config.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @Author zhangtusheng
 * @Date 2023 03 12 21 09
 * @describe：
 **/
@Component
public class CaptchaAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String mobile = authentication.getPrincipal().toString();
        String captcha = ((SmsCaptchaAuthenticationToken) authentication).getCaptcha();
        if (!captchaService.validateCaptcha(captcha)) {
            // 此处进行其他登录验证逻辑，例如验证用户名和密码是否正确
            throw new BadCredentialsException("验证码不正确");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        SmsCaptchaAuthenticationToken result = new SmsCaptchaAuthenticationToken(
                userDetails.getUsername(), captcha, userDetails.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCaptchaAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

