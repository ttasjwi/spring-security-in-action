# SSIA
## Chapter 03
### Ex01. UserDetails, UserDetailsService 구현

---

## UserDetails
```java
public interface UserDetails extends Serializable {

    // 사용자 자격증명 (인증)
    String getUsername();
    String getPassword();
    
    // 사용자 권한 (인가)
	Collection<? extends GrantedAuthority> getAuthorities();
	boolean isAccountNonExpired();
	boolean isAccountNonLocked();
	boolean isCredentialsNonExpired();
	boolean isEnabled();

}
```
- 인증
  - `getUsername()` : 사용자 이름
  - `getPassword()` : 사용자 비밀번호
- 인가
  - `getAuthorities()` : 사용자의 권한들을 컬렉션으로 반환
  - `isAccountNonExpired()` : 계정 만료
  - `isAccountNonLocked()` : 계정 잠금
  - `isCredentialsNonExpired()` : 자격증명 만료
  - `isEnabled()` : 계정 비활성화

---

## GrantedAuthority
```java
public interface GrantedAuthority extends Serializable {

	String getAuthority();

}
```
- 사용자에게 허가된 권한
- 사용자의 권한을 문자열로 반환할 책임
- 일반적으로 사용자는 하나 이상의 권한을 가진다.

---

## Dummy로 사용자 구현
```java
public class DummyUser implements UserDetails {

    @Override
    public String getUsername() {
        return "user";
    }

    @Override
    public String getPassword() {
        return "1111";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "READ");
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
```
단순하게 인터페이스를 구현했다.
- 항상 id, password가 "user", "password"
- 권한은 "READ" 하나를 가지고 있음
- 만료, 잠금, 자격증명 만료, 비활성화 어디에서 해당 안 됨

---

## SimpleUser
```java

public class SimpleUser implements UserDetails {

    private final String username;
    private final String password;

    public SimpleUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
    
    // 생략
}

```
사용자 이름과 암호를 사용자의 상태로 가지도록 정의했다.

---

## User.UserBuilder를 사용하여 인스턴스 만들기
```java
	public static void main(String[] args) {
		UserDetails u1 = User.builder()
				.username("user")
				.password("1111")
				.passwordEncoder(p -> p)
				.accountExpired(false)
				.disabled(true)
				.build(); // 빌더 끝에서 build() 메서드를 호출한다.
		
		UserDetails u2 = User
				.withUserDetails(u1) // 기존의 UserDetails 인스턴스로부터 사용자를 만들 수 있음
				.build();
	}
```
- User.UserBuilder를 사용하여 UserDetails 인스턴스를 만들 수 있다.

---
