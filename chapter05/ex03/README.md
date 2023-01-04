# SSIA
## Chapter 05 - 인증 구현
### ex03 - HTTP BASIC 인증

---

## HTTP BASIC 인증 활성화
```java
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests().anyRequest().authenticated();
    }
}
```
- `http.httpBasic()` : HTTP BASIC 인증 활성화

---
