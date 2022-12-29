package com.ttasjwi.ssia.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class DelegatingPasswordEncoderTest {

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        Map<String, PasswordEncoder> encoders = Map.of(
                "noop", NoOpPasswordEncoder.getInstance(),
                "bcrypt", new BCryptPasswordEncoder(),
                "scrypt", new SCryptPasswordEncoder()
        );

        passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
    }

    @Test
    public void test() {
        CharSequence rawPassword = "캬루";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        log.info("rawPassword = {}", rawPassword);
        log.info("encodedPassword = {}", encodedPassword);

        boolean result = passwordEncoder.matches(rawPassword, encodedPassword);
        log.info("result = {}", result);

        assertThat(encodedPassword.startsWith("{bcrypt}")).isTrue();
        assertTrue(result);
    }
}
