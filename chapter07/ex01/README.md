# SSIA
## Chapter 07 : 권한 부여 구성 - 액세스 제한
### EX 01 : 사용자 권한을 기준으로 모든 엔드포인트에 접근 제한

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

## 사용자 권한을 기준으로 모든 엔드포인트에 접근 제한
```java

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.authorizeRequests()
                .anyRequest()
                //.permitAll(); // 인증 여부에 관계 없이 모든 요청에 대해 접근 허용
                //.hasAuthority("WRITE"); // WRITE 권한을 가진 사용자에 대해서만 접근 허용
                //.hasAnyAuthority("WRITE", "READ"); // WRTIE 또는 READ 권한을 가진 사용자에 대해서 접근 허용
                .access("hasAuthority('WRITE') and !hasAuthority('READ')"); // SpEL 식을 이용하여 조건 지정
    }
}
```
- `permitAll()` : 모든 요청에 대해서 접근 허용
- `hasAuthority()` : 지정 권한을 가진 사용자에 대해서만 접근 허용
- `hasAnyAuthorities()` : 해당 권한들 중 하나라도 가진 사용자에 대해서 접근 허용
- `access()` : SpEL 식을 이용하여 조건 지정

---
