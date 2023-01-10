# SSIA
## Chapter 07 : 권한 부여 구성 - 액세스 제한

---

## GrantedAuthority
```java
public interface GrantedAuthority extends Serializable {
	String getAuthority();
}
```
- 사용자 권한을 나타내는 계약
- 하나의 UserDetails는 하나 이상의 권한을 가진다.
- 이 계약은 `getAuthority()` 메시지에 응답할 책임을 가지는데, String 형식으로 권한의 이름을 반환해야한다.

---

## UserDetails 계약의 `getAuthorities()`
```java
public interface UserDetails extends Serializable {

	Collection<? extends GrantedAuthority> getAuthorities();
    
    // 생략
}
```
- UserDetails는 `getAuthorities()`를 통해, GrantedAuthority 컬렉션을 반환해야한다.

---

## 사용자 관리 설정(UserDetailsService, PasswordEncoder)
```java

@Configuration
public class UserManagementConfig {
    
    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();
        
        var user1 = User.builder()
                .username("john")
                .password("12345")
                .authorities("READ")
                .build();
        
        var user2 = User.builder()
                .username("jane")
                .password("12345")
                .authorities("WRITE")
                .build();
        
        manager.createUser(user1);
        manager.createUser(user2);
        
        return manager;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
```
- john : 읽기 권한 (READ)
- jane : 쓰기 권한 (WRITE)

---
