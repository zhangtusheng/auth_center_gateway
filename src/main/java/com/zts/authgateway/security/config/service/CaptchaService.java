package com.zts.authgateway.security.config.service;

import org.springframework.stereotype.Component;

/**
 * @Author zhangtusheng
 * @Date 2023 03 12 21 10
 * @describe：
 **/
@Component
public class CaptchaService {


    public boolean validateCaptcha(String captcha) {
        return "123456".equals(captcha);
    }
}
