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
