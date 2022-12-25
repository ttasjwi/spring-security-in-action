# SSIA
## Chapter 02
### Ex05. AuthenticationProvider 재정의

---

## AuthenticationProvider 재정의

```java
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        if ("user".equals(userName) && "1111".equals(password)) {
            return new UsernamePasswordAuthenticationToken(userName, password, Arrays.asList());
        }
        throw new AuthenticationCredentialsNotFoundException("Error in authentication");
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticationType);
    }
}
```
- 이 방식에서는 AuthenticationProvider가 자체적으로 인증 로직을 구현했다.
- AuthenticationProvider가 UserDetailsService, PasswordEncoder와 협력하여 인증 로직을 구현하는 것이 스프링 시큐리티의 구현 방식이다.
  - 이후 학습을 거쳐서 이 방식을 통해 구현할 수 있도록 하자.

## AuthenticationProvider 등록
```java
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationProvider authenticationProvider;

    public ProjectConfig(AuthenticationProvider authenticationProvider) {
        super(false);
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic(); // HTTP Basic 방식으로 인증

        http.authorizeRequests()
                .anyRequest().authenticated(); // 모든 요청에 대해 인증
    }
}
```
- configure에서 우리가 구현한 authenticationProvider를 설정하면 된다.

---
