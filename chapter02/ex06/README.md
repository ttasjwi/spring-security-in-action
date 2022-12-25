# SSIA
## Chapter 02
### Ex06. 사용자 관리 구성 - 권한 부여 구성 분리

---

## 사용자 관리, 암호 관리를 위한 구성 클래스 분리
```java

@Configuration
public class UserManagementConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();

        UserDetails user = User.withUsername("user")
                .password("1111")
                .authorities("read")
                .build();

        userDetailsService.createUser(user);
        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}

```
- 사용자 관리, 패스워드 관리 구성을 분리했다.

---

## 권한 부여(인가)를 위한 구성 클래스 정의
```java
@Configuration
public class WebAuthorizationConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic(); // HTTP Basic 방식으로 인증

        http.authorizeRequests()
                .anyRequest().authenticated(); // 모든 요청에 대해 인증
    }
}
```
- WebSecurityConfigAdapter를 확장한 클래스에서는 권한 부여에 관힌 로직에 집중한다. 

---
