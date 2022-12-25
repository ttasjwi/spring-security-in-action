# SSIA
## Chapter 02
### Ex04. configure 방식 간소화(권장 x)

---

## configure 방식 간소화(권장 x)
```java

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("1111")
                .authorities("read")
            .and()
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
```
애플리케이션의 책임을 분리해서 작성하는 것이 좋기 때문에 저자는 이 접근 방식을 권장하지 않는다.

---
