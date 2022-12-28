package com.ttasjwi.ssia.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class PlainTextPasswordEncoderTest {

    @Test
    public void plainTestPasswordEncoderTest() throws Exception {
        var passwordEncoder = new PlainTextPasswordEncoder();
        CharSequence rawPassword = "캬루";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        log.info("rawPassword = {}", rawPassword);
        log.info("encodedPassword = {}", encodedPassword);

        boolean result = passwordEncoder.matches(rawPassword, encodedPassword);
        log.info("result = {}", result);

        assertTrue(result);
    }

}
