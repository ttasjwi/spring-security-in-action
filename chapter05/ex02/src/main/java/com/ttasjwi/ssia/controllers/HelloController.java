package com.ttasjwi.ssia.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(Authentication a) {// 스프링 부트가 현재 Authentication을 메서드 매개 변수에 주입한다.
        // SecurityContext context = SecurityContextHolder.getContext();
        // Authentication a = context.getAuthentication();

        return "Hello, "+ a.getName() + "!";
    }
}
