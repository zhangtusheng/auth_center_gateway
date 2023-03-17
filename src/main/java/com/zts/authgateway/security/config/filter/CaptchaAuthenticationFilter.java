package com.zts.authgateway.security.config.filter;

import com.zts.authgateway.security.config.SmsCaptchaAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author zhangtusheng
 * @Date 2023 03 12 23 23
 * @describe：
 **/
public class CaptchaAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final String phoneParameter = "phone";
    private final String captchaParameter = "captcha";

    public CaptchaAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login/captcha", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String phone = request.getParameter(phoneParameter);
        String captcha = request.getParameter(captchaParameter);


        if (phone == null||captcha == null) {
            throw new BadCredentialsException("手机号或验证码不能为空");
        }

        // 将手机号和验证码封装成 CaptchaAuthenticationToken 对象
        SmsCaptchaAuthenticationToken authRequest = new SmsCaptchaAuthenticationToken(phone, captcha);

        // 进行认证
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}

