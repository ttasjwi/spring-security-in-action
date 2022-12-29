package com.ttasjwi.ssia.keygen;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

@Slf4j
public class StringKeyGeneratorTest {

    @Test
    public void test() throws Exception {
        StringKeyGenerator keyGenerator = KeyGenerators.string();
        String salt = keyGenerator.generateKey();
        log.info("salt = {}", salt); // 8바이트 키(솔트)를 생성하고, 이를 16진수 문자열로 인코딩한다.
    }
}
