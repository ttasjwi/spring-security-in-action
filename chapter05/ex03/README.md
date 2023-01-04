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

## HTTP BASIC 인증 - 인증 실패 커스터마이징

### realm 설정
```java
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic(configure ->
                configure.realmName("OTHER")
        );
        http.authorizeRequests().anyRequest().authenticated();
    }
}
```
```shell
curl -v http://localhost:8080/hello
```
```shell
< WWW-Authenticate: Basic realm="OTHER"
```
- `http.httpBasic()` 메서드는 매개변수로 `Customizer<HttpBasicConfigure<HttpSecurity>>`를 가질 수 있다.
  - 이는 람다식으로 대체 가능하다.
  - 이 람다식은 `HttpBasicConfigure<HttpSecurity>`을 매개변수로 하여, HTTP BASIC 인증의 커스텀 설정을 수행할 수 있다.
- `configure.realmName(...)` : 인증 실패 시 WWW-Authentication 헤더의 realm을 변경
  - realm : 보호 영역의 이름. 사용자가 접근하고자 하는 리소스의 보호 영역을 명시하여, 사용자가 권한에 대한 범위를 이해하는데 도움이 되도록
  하기 위함

---
