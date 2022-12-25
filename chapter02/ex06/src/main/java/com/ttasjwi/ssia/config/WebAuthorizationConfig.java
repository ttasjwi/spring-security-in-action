package com.ttasjwi.ssia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebAuthorizationConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic(); // HTTP Basic 방식으로 인증

        http.authorizeRequests()
                .anyRequest().authenticated(); // 모든 요청에 대해 인증
    }
}
