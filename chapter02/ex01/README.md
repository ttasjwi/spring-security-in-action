# SSIA
## Chapter 02
### Ex01. 스프링 시큐리티 기본 프로젝트

---

## 애플리케이션 구동
![console](imgs/console.png)

- 애플리케이션을 로딩하면 랜덤 패스워드가 생성된다.

---

## 접속 시도
```shell
curl http://localhost:8080/hello
```
```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Unauthorized",
  "path": "/hello"
}
```
- 인증을 위한 올바른 자격증명을 제공하지 않았기 때문에 401 권한 없음(인증 실패)

---

## id, password를 함께 전달
```shell
$ curl -u user:3819dcee-10bd-4aba-9603-ed47b32dcdc9 http://localhost:8080/hello
Hello!
```
- 이번엔 정상적으로 응답이 반환된다.

---

## Http Basic 인증을 통한 엔드포인트 호출
```shell
$ echo -n user:3819dcee-10bd-4aba-9603-ed47b32dcdc9 | base64
dXNlcjozODE5ZGNlZS0xMGJkLTRhYmEtOTYwMy1lZDQ3YjMyZGNkYzk=
```
이 명령어를 입력하면 `user:패스워드` 문자열을 base64 인코딩한 문자열로 전달받는다

```shell
$ curl -H "Authorization: Basic dXNlcjozODE5ZGNlZS0xMGJkLTRhYmEtOTYwMy1lZDQ3YjMyZGNkYzk=" http://localhost:8080/hello
Hello!
```
위와 같이 전달하면, -u 옵션을 사용할 때와 같은 결과를 얻는다.

---

## 테스트 코드
```shell

@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Authentication 없이 /hello 엔드포인트를 호출하면 unauthorized 상태코드를 반환한다.")
    public void helloUnauthenticated() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("MockUser로 /hello 엔드포인트를 호출하면 200 상태코드와 함께 Hello! 를 반환한다.")
    @WithMockUser
    public void helloAuthenticated() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(content().string("Hello!"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("실제 사용자가 /hello 엔드포인트를 호출하면 200 상태코드와 함께 Hello! 를 반환한다.")
    public void helloAuthenticatedWithUser() throws Exception {
        mockMvc.perform(get("/hello")
                        .with(user("mary")))
                .andExpect(content().string("Hello!"))
                .andExpect(status().isOk());
    }
}
```
- 이 예제에서 사용된 테스트 코드는 위와 같다.
- `@WithMockUser` : https://gaemi606.tistory.com/entry/Spring-Boot-Spring-Security-Test-WithMockUser%EB%A5%BC-%EC%BB%A4%EC%8A%A4%ED%84%B0%EB%A7%88%EC%9D%B4%EC%A7%95-%ED%95%B4%EC%84%9C-%EC%82%AC%EC%9A%A9%ED%95%98%EC%9E%90

---
