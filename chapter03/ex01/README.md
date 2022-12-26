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

## UserDetails 구현체와 엔티티 분리
```java

public class SecurityUser implements UserDetails {

    private final Member member; // 사용자 엔티티와 UserDetails 역할을 분리한다.

    public SecurityUser(Member member) {
        this.member = member;
    }

    @Override
    public String getUsername() {
        return member.getName();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(()-> member.getAuthority());
    }
    
    // 생략
}
```

- 사용자 엔티티가 UserDetails 역할을 같이 수행하게 되면, 사용자 엔티티는 변경의 이유가 두가지가 된다. 이는 단일 책임 원칙을 위반한다.
  - 도메인 규칙
  - 인증, 인가에 관련된 로직
- UserDetails 구현체가 사용자 엔티티를 의존하게 하고 인증에 관한 로직은 이 곳에 두도록 하면 된다.

---

## UserDetailsService
```java
public interface UserDetailsService {

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
```
- UserDetailsService는 사용자 이름으로 사용자를 검색하는 역할을 수행한다.
- UsernameNotFoundException
  - 지정 이름의 사용자를 찾지 못 했을 경우 발생하는 예외
  - 예외 계층 : RuntimeException <- AuthenticationException <- UsernameNotFoundException

---

## 커스텀 UserDetailsService
```java
public class MyInMemoryUserDetailsService implements UserDetailsService {

    private final List<UserDetails> users = new ArrayList<>();

    public MyInMemoryUserDetailsService(List<UserDetails> users) {
        this.users.addAll(users);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
```
- 메모리 저장소에 접근하여 사용자 인증 정보를 가져오는 UserDetailsService를 구현했다.

---
