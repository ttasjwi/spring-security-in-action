# SSIA
## Chapter 07 : 권한 부여 구성 - 액세스 제한
### EX 04 : access() 심화 / 모든 엔드포인트 접근 제한

---

## access() 심화 (SpEL)
```java

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.authorizeRequests()
                .anyRequest()
                .access("T(java.time.LocalTime).now().isAfter(T(java.time.LocalTime).of(12,0))");
    }
}
```
- `access()`에서, SpEL을 사용하면 웬만한 규칙을 거의 다 구현할 수 있다.
- SpEL : https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#expressions

---
