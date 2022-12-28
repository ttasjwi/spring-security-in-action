package com.ttasjwi.ssia.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class SCryptPasswordEncoderTest {

    @Test
    public void test1() throws Exception {
        var passwordEncoder = new SCryptPasswordEncoder();
        CharSequence rawPassword = "캬루";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        log.info("rawPassword = {}", rawPassword);
        log.info("encodedPassword = {}", encodedPassword);

        boolean result = passwordEncoder.matches(rawPassword, encodedPassword);
        log.info("result = {}", result);

        assertTrue(result);
    }

    @Test
    public void test2() throws Exception {
        var passwordEncoder = new SCryptPasswordEncoder(16384, 8, 1, 32, 64);
        CharSequence rawPassword = "캬루";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        log.info("rawPassword = {}", rawPassword);
        log.info("encodedPassword = {}", encodedPassword);

        boolean result = passwordEncoder.matches(rawPassword, encodedPassword);
        log.info("result = {}", result);

        assertTrue(result);
    }
}
