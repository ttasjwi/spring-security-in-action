# SSIA
## Chapter 06: 실전 - 작고 안전한 웹 애플리케이션

---

## PasswordEncoder
```java

@Configuration
public class PasswordEncoderConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SCryptPasswordEncoder sCryptPasswordEncoder() {
        return new SCryptPasswordEncoder();
    }

}
```
- 두 가지 패스워드 인코더를 사용하는 예제의 편의성을 위해 구체 클래스로 빈 등록을 했다.

## UserDetailsService
```java
@Service
@RequiredArgsConstructor
public class UserDetailsInputPort implements UserDetailsService {

    private final MemberOutputPort memberOutputPort;

    @Override
    public SecurityMember loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberOutputPort.findByNameWithAuthorities(username);
        return new SecurityMember(member);
    }
}
```
- UserDetailsService의 구현체를 member-application 헥사곤의 input-port로 만들었다.
  - 이게 맞는지는 모르겠다.
- 사용자 도메인을 가져와, SecurityMember로 감싼다.

## AuthenticationProvider
```java

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SCryptPasswordEncoder sCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        SecurityMember userDetails = (SecurityMember) userDetailsService.loadUserByUsername(username);

        switch (userDetails.getMember().getAlgorithm()) {
            case BCRYPT:
                return checkPassword(userDetails, password, bCryptPasswordEncoder);
            case SCRYPT:
                return checkPassword(userDetails, password, sCryptPasswordEncoder);
        }

        throw new BadCredentialsException("bad credentials");
    }

    private Authentication checkPassword(UserDetails userDetails, String rawPassword, PasswordEncoder encoder) {
        if (encoder.matches(rawPassword, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        }
        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
```
- 예제의 편의를 위해 구체 클래스들을 사용했다.
- 사용자의 패스워드 인코딩 알고리즘을 확인 후, 이를 기반으로 어떤 인코더인지 확인하여 이용하도록 했다.

---

## SecurityConfig
```java
@Configuration
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationProvider authenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .defaultSuccessUrl("/main", true);
        http.authorizeRequests().anyRequest().authenticated();
    }
}
```

---

## 로그인
- id : john
- pw : 12345
- 주소 : https://localhost:8080/main

---
