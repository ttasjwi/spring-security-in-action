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

### AuthenticationEntryPoint 사용
```java
public class CustomEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.addHeader("my-message", "Luke, I am your father!!!");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
```
```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic(configure -> {
            configure.realmName("OTHER");
            configure.authenticationEntryPoint(new CustomEntryPoint());
        });
        http.authorizeRequests().anyRequest().authenticated();
    }
```
- 스프링 시큐리티 아키텍쳐의 ExceptionTranslationManager는 인증이 실패했을 때, 필터체인에서 발생한 AuthenticationException,
AccessDeniedException 을 처리한다.
- 이때 ExceptionTranslationManager는 AuthenticationEntryPoint 역할을 참조한다.
- 우리가 AuthenticationEntryPoint 구현체를 만들어 스프링 시큐리티에 등록하면, 이를 스프링 시큐리티의 ExceptionTranslationManager가
사용하게 된다.
```shell
curl -v http://localhost:8080/hello
```
```shell
< HTTP/1.1 401
< Set-Cookie: JSESSIONID=D28608755216BA262D430837B8AB0A58; Path=/; HttpOnly
< my-message: Luke, I am your father!!!
```
- HTTP BASIC 인증이 실패했을 때, 우리가 지정한 커스텀 엔트리포인트의 설정에 따라, 응답에 새로 추가된 헤더를 볼 수 있다.

---

