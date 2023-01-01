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
