package com.ttasjwi.ssia.encryptor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

@Slf4j
public class TextEncryptorTest {

    @Test
    public void test1() throws Exception {
        String valueToEncrypt = "캬루";
        log.info("rawString = {}", valueToEncrypt);

        TextEncryptor encryptor = Encryptors.noOpText();
        String encrypted = encryptor.encrypt(valueToEncrypt);
        String decrypted = encryptor.decrypt(encrypted);
        log.info("encrypted = {}", encrypted);
        log.info("decrypted = {}", decrypted);
    }

    @Test
    public void test2() throws Exception {
        String password = "secret";
        String salt = KeyGenerators.string().generateKey();
        String valueToEncrypt = "캬루";
        log.info("rawString = {}", valueToEncrypt);

        //TextEncryptor encryptor = Encryptors.text(password, salt); // 내부적으로 Encryptors.standard() 사용
        TextEncryptor encryptor = Encryptors.delux(password, salt); // 내부적으로 Encryptors.stronger() 사용
        String encrypted = encryptor.encrypt(valueToEncrypt);
        String decrypted = encryptor.decrypt(encrypted);
        log.info("encrypted = {}", encrypted);
        log.info("decrypted = {}", decrypted);
    }

    @Test
    public void test3() throws Exception {
        String password = "secret";
        String salt = KeyGenerators.string().generateKey();
        String valueToEncrypt = "캬루";
        log.info("rawString = {}", valueToEncrypt);

        TextEncryptor encryptor = Encryptors.queryableText(password, salt);
        String encrypted1 = encryptor.encrypt(valueToEncrypt);
        String encrypted2 = encryptor.encrypt(valueToEncrypt);
        log.info("encrypted1 = {}", encrypted1);
        log.info("encrypted2 = {}", encrypted2); // 매번 같은 결과
    }

}
