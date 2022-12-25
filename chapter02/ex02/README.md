# SSIA
## Chapter 02
### Ex02. 기본 구성 재정의(UserDetailsService, PasswordEncoder)

---

## UserDetailsService 수동 빈 등록
```java
@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
        return userDetailsService;
    }
}
```
UserDetailsService 만을 수동 빈 등록하면 두가지 이유로 엔드포인트에 접근할 수 없다.
- 기본 등록되는 사용자가 없다. (이는 사용자를 추가함으로서 해결할 수 있다.)
  - 기본 등록되는 사용자가 없어서, 콘솔에 password도 출력되지 않는다.
- PasswordEncoder가 없다.

---
