package com.ttasjwi.ssia.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class BCryptPasswordEncoderTest {

    @Test
    public void test1() throws Exception {
        var passwordEncoder = new BCryptPasswordEncoder();
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
        var passwordEncoder = new BCryptPasswordEncoder(4);
        CharSequence rawPassword = "캬루";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        log.info("rawPassword = {}", rawPassword);
        log.info("encodedPassword = {}", encodedPassword);

        boolean result = passwordEncoder.matches(rawPassword, encodedPassword);
        log.info("result = {}", result);

        assertTrue(result);
    }

    @Test
    public void test3() throws Exception {
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        var passwordEncoder = new BCryptPasswordEncoder(4, secureRandom);

        CharSequence rawPassword = "캬루";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        log.info("rawPassword = {}", rawPassword);
        log.info("encodedPassword = {}", encodedPassword);

        boolean result = passwordEncoder.matches(rawPassword, encodedPassword);
        log.info("result = {}", result);

        assertTrue(result);
    }
}
