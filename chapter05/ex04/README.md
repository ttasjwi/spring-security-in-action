# SSIA
## Chapter 05 - 인증 구현
### ex04 - Form 인증

---

## Form 인증 활성화, 디폴트 성공 URL 지정

```java
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .defaultSuccessUrl("/home", true);

        http.authorizeRequests()
                .anyRequest()
                .authenticated();
    }
}
```
- `http.formLogin()` : Form 로그인 활성화. 이 결과값은 `FormLoginConfigure<HttpSecurity>`를 반환하는데, 
이를 이용해 커스텀 form 로그인 설정을 할 수 있다.
  - `.defaultSuccesUrl(...)` : 로그인이 성공한 이후에 이동할 페이지 경로

```java
@Controller
public class HelloController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
```
- `/home` 엔드포인트로 GET 요청 시, `/home.html`이 렌더링되도록 함
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Home</title>
</head>
<body>
    <h1>Welcome</h1>
</body>
</html>
```
- `src/main/resources/templates/home.html` 은 간단한 샘플 페이지만 둠

여기까지 한 상태로 `http://localhost:8080` 으로 접속을 시도하면 `http://localhost:8080/login` 으로 리다이렉트 되며,
여기서 로그인을 진행하면 자동으로 `http://localhost:8080/home` 으로 리다이렉트 된다.

---

## Form 로그인 성공/실패 처리 커스텀 핸들러
```java
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if (hasReadAuthority(authentication)) {
            response.sendRedirect("/home");
            return;
        }
        response.sendRedirect("/error");
    }

    private static boolean hasReadAuthority(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .filter(grantedAuthority -> grantedAuthority.getAuthority().equals("read"))
                .findFirst()
                .isPresent();
    }
}
```
- 로그인 성공 시 처리 로직을 작성할 수 있다.
```java

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        response.setHeader("failed", LocalDateTime.now().toString());
    }
}
```
- 로그인 실패 시 처리 로직을 작성할 수 있다.
```java

@Configuration
@RequiredArgsConstructor
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .successHandler(successHandler)
                .failureHandler(failureHandler)
            .and()
                .httpBasic();

        http.authorizeRequests()
                .anyRequest()
                .authenticated();
    }
}
```
- 상세한 성공, 실패 로직 작성시, 위와 같이 successHandler, failureHandler 에게 책임을 분리하면 응집도를 높일 수 있다.
- Form 로그인 방식과 HTTP BASIC 방식을 동시에 사용할 수 있다.

---
