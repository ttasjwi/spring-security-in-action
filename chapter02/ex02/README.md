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

## 기본 사용자 등록
```java
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();

        UserDetails user = User.withUsername("user")
                .password("1111")
                .authorities("read") // 아무런 권한을 하나 만들어서 지정해준다.
                .build();

        userDetailsService.createUser(user);
        return userDetailsService;
    }
```
- 기본 사용자를 생성하여 등록한다.
  - authorities : 권한을 하나 이상 설정해줘야하는데, 일단 형식 상 하나 지정해준다.
```shell
curl -u user:1234 http://localhost:8080/hello
```
```shell
java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
```
하지만 아직 PasswordEncoder를 빈으로 등록하지 않았기 때문에 예외가 발생한다.

---

## PasswordEncoder
```java

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // 암호 인코딩 없이, 평문으로 비교(Deprecated)
    }
```
- 기능이 일단 돌아갈 수 있도록, NoOpPasswordEncoder 를 빈으로 등록한다.
- 이것은 암호를 별도로 인코딩하지 않고 평문으로 비교한다. (그래서 공식적으로 Deprecated됨)
```shell
$ curl -u user:1111 http://localhost:8080/hello                                                      
Hello!
```
이제 정상적으로 엔드포인트 호출에 성공한다.

---

## 엔드포인트 권한 부여 구성 재정의
```java
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic(); // HTTP Basic 방식으로 인증

        http.authorizeRequests()
                .anyRequest().authenticated(); // 모든 요청에 대해 인증

        // http.authorizeRequests().anyRequest().permitAll(); // 모든 요청에 대해 인증 없이 허락
    }
}
```
- WebSecurityConfigurerAdapter 클래스를 확장하여, 인증을 위한 권한 부여 구성 방법을 재정의한다.
  - 기본적으로 HttpBasic 방식으로 인증 하도록 한다.
  - 모든 요청에 대해 인증을 필요로 하도록 설정
- WebSecurityConfigurerAdapter 클래스를 확장한 클래스는 단 하나만 존재해야 한다.

---
