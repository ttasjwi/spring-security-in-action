# SSIA
## Chapter 08 : 권한 부여 구성 - 제한 적용
### EX 01 - MVC Matcher(MVC 선택기)

---

## 선택기(Matcher) 메서드
- 특정한 요청 그룹에만 권한 부여 제약 조건을 적용하는 메서드
- 종류) anyRequest(), MVC 선택기, 앤트 선택기, 정규표현식 선택기, ...
  - anyRequest() : 모든 요청 또는 '다른 모든 요청'
  - MVC Matcher : 경로에 MVC 식을 이용해 엔드포인트 선택
  - 앤트 Matcher : 경로에 앤트 식을 이용해 엔드포인트 선택
  - 정규표현식 Matcher : 정규표현식(regex)을 이용해 엔드포인트 선택

---

## 사용자 관리 설정

### UserManagementConfig
```java
@Configuration
public class UserManagementConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("john")
                .password("12345")
                .roles("ADMIN")
                .build();

        var user2 = User.withUsername("jane")
                .password("12345")
                .roles("MANAGER")
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
- InMemoryUserManager와 NoOpPasswordEncoder를 사용한다.
- john에게는 ADMIN 역할을 부여했다.
- jane에게는 MANAGER 역할을 부여했다.

---

## MVCMatcher 적용
### WebSecurityConfig - MvcMatcher 적용
```java
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.authorizeRequests()
                .mvcMatchers("/hello").hasRole("ADMIN")
                .mvcMatchers("/ciao").hasRole("MANAGER");
    }
}
```
- MVC Matcher 사용
- "/hello" 엔드포인트 요청에 대해서는 "ADMIN" 역할이 필요
- "/ciao" 엔드포인트 요청에 대해서는 "MANAGER" 역할이 필요


### 엔드포인트 호출
```shell
curl -u john:12345 http://localhost:8080/hello
```
```shell
Hello!
```
```shell
curl -u john:12345 http://localhost:8080/ciao
```
```json
{
  "timestamp": "2023-01-17T12:28:57.716+00:00",
  "status": 403,
  "error": "Forbidden",
  "path": "/ciao"
}
```
- John은 ADMIN 역할이 있으므로, `/hello` 엔드포인트 호출 가능
- 하지만 MANAGER 역할이 없으므로 `/ciao` 엔드포인트 호출이 불가능

```shell
curl -u jane:12345 http://localhost:8080/ciao
```
```shell
Ciao!
```
```shell
curl -u jane:12345 http://localhost:8080/hello
```
```json
{
  "timestamp": "2023-01-17T12:28:21.623+00:00",
  "status": 403,
  "error": "Forbidden",
  "path": "/hello"
}
```
- Jane은 MANAGER 역할이 있으므로, `/ciao` 엔드포인트 호출 가능
- 하지만 ADMIN 역할이 없으므로 `/hello` 엔드포인트 호출이 불가능


---

