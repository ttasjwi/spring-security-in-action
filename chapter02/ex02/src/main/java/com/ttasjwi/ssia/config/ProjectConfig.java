package com.ttasjwi.ssia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();

        UserDetails user = User.withUsername("user")
                .password("1111")
                .authorities("read") // 아무런 권한을 하나 만들어서 지정해준다.
                .build();

        userDetailsService.createUser(user);
        return userDetailsService;
    }
}
