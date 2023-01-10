# SSIA
## Chapter 07 : 권한 부여 구성 - 액세스 제한
### EX 02 : 더 복잡한 식을 이용하는 access() 메서드 적용

---

## 사용자 권한 설정
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
                .authorities("READ", "WRITE", "DELETE")
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
- john에게는 READ 권한
- jane에게는 READ 권한, WRITE 권한, DELETE 권한을 주었다.

---

## aaccess() 메서드 적용
```java

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

        String expression = "hasAuthority('READ') and !hasAuthority('DELETE')";

        http.authorizeRequests()
                .anyRequest()
                .access(expression);
    }
}
```
- READ 권한을 가지고 있으면서, DELETE 권한을 가지지 않은 계정에 대해서 요청을 허용한다.

---

## 결과
```shell
curl -u john:12345 http://localhost:8080/hello
```
```shell
curl -u jane:12345 http://localhost:8080/hello
```
- john은 `/hello` 엔드 포인트에 접근할 수 있다.
- jane은 `/hello` 엔드 포인트에 접근할 수 없고, HTTP 상태 코드로 403 Forbidden 을 응답 받는다.

---
