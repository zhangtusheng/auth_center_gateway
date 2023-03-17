package com.zts.authgateway.security.config.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangtusheng
 * @Date 2023 03 12 18 19
 * @describe：
 **/
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            logger.info("用户名不存在，用户名为：" + username);
            throw new UsernameNotFoundException("用户不存在");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        User user = new User("root", "123456", authorities);
        return user;
    }
}
