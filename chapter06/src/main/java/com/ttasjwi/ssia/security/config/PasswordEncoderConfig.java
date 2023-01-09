package com.ttasjwi.ssia.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.Map;

@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> passwordEncoders = Map.of(
                "bCrypt", new BCryptPasswordEncoder(),
                "sCrypt", new SCryptPasswordEncoder()
        );
        return new DelegatingPasswordEncoder("bCrypt", passwordEncoders);
    }

}
