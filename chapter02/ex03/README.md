# SSIA
## Chapter 02
### Ex03. 다른 방식으로 구성 설정(UserDetailsService, PasswordEncoder)

---

## configure 메서드를 통해 UserDetailsService, PasswordEncoder를 설정하는 방법
```java
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();

        UserDetails user = User.withUsername("user")
                .password("1111")
                .authorities("read") // 아무런 권한을 하나 만들어서 지정해준다.
                .build();

        userDetailsService.createUser(user);

        PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance(); // 암호 인코딩 없이, 평문으로 비교(Deprecated)

        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
```
- configure 메서드를 오버라이드 함으로서 UserDetailsService, PasswordEncoder를 설정할 수 있다.

---

## 주의
- UserDetailsService, PasswordEncoder 를 스프링 빈으로 등록할 수 있다.
- configure 메서드를 오버라이드 하여 이들을 설정할 수 있다.
- 하지만 이 구성방식을 혼합하여 사용하면 헷갈릴 수 있으므로 섞어 쓰지 말자

---
