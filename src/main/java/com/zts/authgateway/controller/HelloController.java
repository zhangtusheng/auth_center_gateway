package com.zts.authgateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author zhangtusheng
 * @Date 2023 03 12 18 06
 * @describe：
 **/
@RestController
public class HelloController {


    @GetMapping("/hello")
    public String helloWorld() {
        return "hello World";
    }


    @GetMapping("/test")
    public String test(HttpServletRequest request,
                       HttpServletResponse response) {
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest request1 = requestCache.getRequest(request, response);
        if (request1!=null) {
            System.out.println(request1.getRedirectUrl());
        }
        return "test";
    }

    @GetMapping("/test1")
    public String test1() {
        return "test1";
    }
}
