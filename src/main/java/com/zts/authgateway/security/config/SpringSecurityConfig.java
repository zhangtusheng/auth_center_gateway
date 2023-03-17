package com.zts.authgateway.security.config;

import com.zts.authgateway.security.config.filter.CaptchaAuthenticationFilter;
import com.zts.authgateway.security.config.provider.CaptchaAuthenticationProvider;
import com.zts.authgateway.security.config.service.AuthPasswordEncoder;
import com.zts.authgateway.security.config.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author zhangtusheng
 * @Date 2023 03 12 20 41
 * @describe：
 **/
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthPasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Autowired
    private CaptchaAuthenticationProvider captchaAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        auth.authenticationProvider(captchaAuthenticationProvider);
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        auth.authenticationProvider(authProvider);
        //auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //设置不需要鉴权的地址
                .antMatchers("/error", "/", "index","/login/captcha", "/login","/login-error", "/401", "/static/**").permitAll()
                //其他请求均需要鉴权
                .anyRequest().authenticated()
                .and()
                //登录成功跳转页面
                .formLogin().defaultSuccessUrl("/hello")
                //登录失败跳转页面
                .and()
                .addFilterBefore(captchaAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedPage("/401")
                .and().csrf().disable()
                .headers().frameOptions().disable();
        http.logout().logoutSuccessUrl("/");
    }

    @Bean
    public CaptchaAuthenticationFilter captchaAuthenticationFilter() throws Exception {
        CaptchaAuthenticationFilter filter = new CaptchaAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setFilterProcessesUrl("/login/captcha");
        return filter;
    }
//
//    @Bean
//    public SavedRequestAwareAuthenticationSuccessHandler successHandler() {
//        SavedRequestAwareAuthenticationSuccessHandler handler =
//                new SavedRequestAwareAuthenticationSuccessHandler();
//        handler.setDefaultTargetUrl("/hello"); //设置登录成功后的默认跳转页面
//        handler.setUseReferer(true); //设置是否使用referer，默认是false
//        return handler;
//    }
}
