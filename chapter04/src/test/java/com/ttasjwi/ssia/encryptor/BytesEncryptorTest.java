package com.ttasjwi.ssia.encryptor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;

@Slf4j
public class BytesEncryptorTest {

    @Test
    public void test() throws Exception {
        String password = "secret";
        String salt = KeyGenerators.string().generateKey();

        String valueToEncrypt = "캬루";
        byte[] rawBytes = valueToEncrypt.getBytes();
        log.info("rawBytes = {}", valueToEncrypt.getBytes());

        //BytesEncryptor encryptor = Encryptors.standard(password, salt); // 더 약함 ---> CBC(암호 블록 체인) 이용
        BytesEncryptor encryptor = Encryptors.stronger(password, salt); // 더 강함 --> 256바이트 AES 암호화, GCM 사용
        byte[] encrypted = encryptor.encrypt(rawBytes);
        byte[] decrypted = encryptor.decrypt(encrypted);
        log.info("encrypted = {}", encrypted);
        log.info("decrypted = {}", decrypted);
    }
}
