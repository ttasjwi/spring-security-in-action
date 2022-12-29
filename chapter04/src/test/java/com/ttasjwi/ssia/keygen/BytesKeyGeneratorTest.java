package com.ttasjwi.ssia.keygen;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class BytesKeyGeneratorTest {

    @Test
    @DisplayName("기본 8바이트")
    public void test1() throws Exception {
        BytesKeyGenerator keyGenerator = KeyGenerators.secureRandom();

        byte[] key = keyGenerator.generateKey();
        int keyLength = keyGenerator.getKeyLength();

        log.info("key(salt) = {}", key);
        log.info("keyLength = {}", keyLength);
    }

    @Test
    @DisplayName("다른 키 길이 지정")
    public void test2() throws Exception {
        BytesKeyGenerator keyGenerator = KeyGenerators.secureRandom(16);

        byte[] key = keyGenerator.generateKey();
        int keyLength = keyGenerator.getKeyLength();

        log.info("key(salt) = {}", key);
        log.info("keyLength = {}", keyLength);
    }

    @Test
    @DisplayName("같은 키(솔트)를 반환하는 구현이 필요할 때")
    public void test3() throws Exception {
        BytesKeyGenerator keyGenerator = KeyGenerators.shared(16);

        byte[] key1 = keyGenerator.generateKey();
        byte[] key2 = keyGenerator.generateKey();

        log.info("key1 = {}", key1);
        log.info("key2 = {}", key2);
        assertThat(key1).isEqualTo(key2);
    }
}
