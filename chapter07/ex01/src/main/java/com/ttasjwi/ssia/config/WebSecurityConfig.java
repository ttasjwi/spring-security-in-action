package com.ttasjwi.ssia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.authorizeRequests()
                .anyRequest()
                //.permitAll(); // 인증 여부에 관계 없이 모든 요청에 대해 접근 허용
                //.hasAuthority("WRITE"); // WRITE 권한을 가진 사용자에 대해서만 접근 허용
                //.hasAnyAuthority("WRITE", "READ"); // WRTIE 또는 READ 권한을 가진 사용자에 대해서 접근 허용
                .access("hasAuthority('WRITE') and !hasAuthority('READ')"); // SpEL 식을 이용하여 조건 지정
    }
}
