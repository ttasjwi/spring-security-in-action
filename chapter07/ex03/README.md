# SSIA
## Chapter 07 : 권한 부여 구성 - 액세스 제한
### EX 03 : 역할(ROLE)을 기준으로 모든 엔드포인트에 대한 접근을 제한

---

## 역할(ROLE)이란?
- 개념 상으로 역할은 여러 권한을 묶어둔 권한 모음이다.
- 역할을 정의하면 사실상 권한을 더 이상 정의할 필요가 없다.
- 예) ROLE_MANAGER <- READ, WRITE
- 예) ROLE_ADMIN <- READ, WRITE, UPDATE, DELETE

## 사용자 관리 설정
```java
@Configuration
public class UserManagementConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("john")
                .password("12345")
                .authorities("ROLE_ADMIN")
                .build();

        var user2 = User.withUsername("jane")
                .password("12345")
                .authorities("ROLE_MANAGER")
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
- 내부적으로 ROLE은 GrantedAuthority 계약을 통해 사용된다.
- 역할은 `ROLE_` 접두사로 시작해야한다.

---

## 사용자 역할 설정
```java

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.authorizeRequests()
                .anyRequest()
                .hasRole("ADMIN");
    }
}
```
- 권한과 마찬가지로 `hasRole`, `hasAnyRole`, `access()`을 통해, 역할에 대한 제약 조건을 걸 수 있다.
- 이때 인자를 넘길 때 `ROLE_`을 접두사가 포함되어선 안 된다.

---

## 사용자 관리설정 (2)
````java

@Configuration
public class UserManagementConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("john")
                .password("12345")
                .roles("ADMIN") // roles
                .build();

        var user2 = User.withUsername("jane")
                .password("12345")
                .roles("MANAGER") // roles
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
````
- 역할 기반의 접근을 설계할 때 `authorities()` 대신 `roles()`를 이용할 수 있다.
- 다만 여기서 roles에 `ROLE_` 접두사가 포함된 인자를 넣어선 안 된다.

---

## 실행
```shell
curl -u john:12345 http://localhost:8080/hello
```
```shell
curl -u jane:12345 http://localhost:8080/hello
```
- john은 ADMIN 역할을 가지고 있으므로 `/hello` 엔드포인트에 접근할 수 있다.
- jane은 ADMIN 역할을 가지고 있지 않으므로 `/hello` 엔드포인트에 접근할 수 없다.

---

## 참고
- spEL : https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#expressions

---
