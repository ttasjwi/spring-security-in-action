# SSIA
## Chapter 04 - 암호 처리(PasswordEncoder)
### Ex01. PasswordEncoder의 구현 및 이용

---

## PasswordEncoder 계약
```java
public interface PasswordEncoder {

	String encode(CharSequence rawPassword);
	boolean matches(CharSequence rawPassword, String encodedPassword);
	default boolean upgradeEncoding(String encodedPassword) {
		return false;
	}

}
```
PasswordEncoder 계약을 통해 스프링 시큐리티에 암호를 검증하는 방법을 알려줄 수 있다.
- `encode(...)` : 지정 문자열을 암호화한다. (기능 관점 해시, 암호화)
- `matches(rawPassword, encodedPassword)` : 평문 문자열과 암호화된 패스워드를 비교하여 일치하는 지 여부를 반환한다.
- `upgradeEncoding(...)` : 인코딩 된 암호를 다시 암호화 할 지의 여부
  - 디폴트는 false
  - true를 반환하도록 오버라이드하면 보안 향상을 위해 다시 인코딩한다.

---

## PasswordEncoder의 가장 단순한 구현
```java
public class PlainTextPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString(); // 암호를 변경하지 않고 그대로 반환
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword); // 두 문자열이 같은지 확인한다.
    }
}
```
- NoOpPasswordEncoder와 동일한 방식의 구현
- 암호를 단순히 일반 텍스트로 취급한다

---

## SHA-512로 입력을 해싱하여 암호화하는 PasswordEncoder 구현
```java

public class Sha512PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return hashWithSHA512(rawPassword.toString());
    }

    private String hashWithSHA512(String input) {
        StringBuilder result = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            byte[] digested = messageDigest.digest(input.getBytes());
            for (byte b : digested) {
                result.append(Integer.toHexString(0xFF & b));
            }
            return result.toString(); // 입력의 해시값을 반환함.
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Bad algorithm", e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String hashedPassword = encode(rawPassword);
        return encodedPassword.equals(hashedPassword);
    }

}
```
- SHA-512 로 원시암호를 해시하여 인코딩하는 방식으로 직접 구현
- 하지만 스프링 시큐리티에서 제공하는 더 좋은 기본 PasswordEncoder 구현체들이 있으므로 차라리 이들을 사용하는 것을 고려해볼만 하다.

---

## 여러가지 기본 PasswordEncoder
### StandardPasswordEncoder
```java
PasswordEncoder p = new StandardPasswordEncoder();
PasswordEncoder p = new StandardPasswordEncoder("secret");
```
- `SHA-256` 알고리즘을 사용하여 해싱
- 이제는 사용하지 않는 것이 좋다. (Deprecated)

### Pdkdf2PasswordEncoder
```java
PasswordEncoder p = new Pbkdf2PasswordEncoder();
PasswordEncoder p = new Pbkdf2PasswordEncoder("secret");
PasswordEncoder p = new Pbkdf2PasswordEncoder("secret", 185000, 256);
```
- PBKDF2로 암호를 인코딩
- secret, iterations, hashWidth
  - secret : 키
  - iterations : 암호 인코딩 반복 횟수
  - hashWidth : 해시의 크기
- iterations, hashWidth 값이 클 수록 강력해지지만, 애플리케이션이 소비하는 리소스가 증가한다.

### BCryptPasswordEncoder
```java
PasswordEncoder p = new BCryptPasswordEncoder();
PasswordEncoder p = new BCryptPasswordEncoder(4);

SecureRandom secureRandom = SecureRandom.getInstanceStrong();
PasswordEncoder p = new BCryptPasswordEncoder(4, secureRandom);
```
- bcrypt 강력 해싱함수를 이용한 패스워드 인코더
- strength, SecureRandom
  - strength : 로그 라운드를 나타내는 강도 계수. 해싱 작업이 이용하는 반복 횟수에 영향을 끼친다.
    - 반복 횟수는 2로그 라운드로 계산되고, 이 값은 4~31 사이여야 한다.
  - SecureRandom : 인코딩에 사용되는 SecureRandom

### SCryptPasswordEncoder
```java
PasswordEncoder p = new SCryptPasswordEncoder();
PasswordEncoder p = new SCryptPasswordEncoder(16384, 8, 1, 32, 64);
```
- cpuCost, memoryCost, parallelization, keyLength, saltLength
  - cpuCost : CPU 비용
  - memoryCost : 메모리 비용
  - parallelization : 병렬화 계수
  - keyLength : 키 길이
  - saltLength : 솔트 길이

---
