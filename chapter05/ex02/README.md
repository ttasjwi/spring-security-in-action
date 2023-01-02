# SSIA
## Chapter 05 - 인증 구현
### ex02 - SecurityContext

---

## SecurityContext
```java
public interface SecurityContext extends Serializable {

	Authentication getAuthentication();
	void setAuthentication(Authentication authentication);

}
```
- 인증 성공 후 인증된 Authentication 객체를 저장, 조회 하는 역할
- 전략
  - `MODE_THREADLOCAL` : 스레드 로컬에 저장
  - `MODE_INHERITABLETHREADLOCAL` : 비동기 메서드의 경우, 다음 스레드에 복사하도록 스프링 시큐리티에 지시
  - `MODE_GLOBAL` : 애플리케이션의 모든 스레드가 같은 SecurityContext 인스턴스를 보게 함

---

## MODE_THREADLOCAL 전략
```java
    @GetMapping("/hello")
    public String hello() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication a = context.getAuthentication();
        return "Hello, "+ a.getName() + "!";
    }
```
```shell
$ curl -u user:3c9dd14d-64e0-4274-a59c-1b480bb44b93 http://localhost:8080/hello                                                                                                                                                    
Hello, user!
```
- SecurityContextHolder를 통해 스레드 로컬에 저장된 SecurityContext를 조회해올 수 있다.

```java
    @GetMapping("/hello")
    public String hello(Authentication a) {// 스프링 부트가 현재 Authentication을 메서드 매개 변수에 주입한다.
        // SecurityContext context = SecurityContextHolder.getContext();
        // Authentication a = context.getAuthentication();
        
        return "Hello, "+ a.getName() + "!"; 
    }
```
- 위와 같이 컨트롤러의 메서드 매개변수로 Authentication을 지정하면, 스프링 부트가 현재 Authentication을 메서드 매개변수에 주입한다.

---

## MODE_INHERITABLETHREADLOCAL 전략

### `@EnableAsync`
```java

@Configuration
@EnableAsync
public class ProjectConfig {

}
```
- `@EnableAsync`를 설정 클래스에 걸어주면 `@ASync` 어노테이션의 기능을 활성화할 수 있다.

### `@ASync`
```java

    @GetMapping("/bye")
    @Async
    public void goodbye() {
        SecurityContext context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        System.out.println(username);
    }
```
- `@ASync` 어노테이션이 걸린 메서드는 이제부터 별도의 스레드에서 실행된다.

### 실행 -> NullPointerException
```shell
$ curl -u user:2268ad51-b702-4983-8f8a-6150d797d1e5 http://localhost:8080/bye
```
```shell
java.lang.NullPointerException: null
	at com.ttasjwi.ssia.controllers.HelloController.goodbye(HelloController.java:26) ~[classes/:na]
	at com.ttasjwi.ssia.controllers.HelloController$$FastClassBySpringCGLIB$$16045f52.invoke(<generated>) ~[classes/:na]
# 생략
```
- 이후 코드를 실행해보면 NullPointerException이 발생한다.
- 해당 메서드가 SecurityContext를 상속하지 않는 다른 스레드에서 실행되기 때문이다.

---
