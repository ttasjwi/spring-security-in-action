# SSIA
## Chapter 05 - 인증 구현
### ex01 - 인증 요청, 커스텀 인증 로직 구현 및 적용

---

## Authentication
```java
public interface Authentication extends Principal, Serializable {

	Collection<? extends GrantedAuthority> getAuthorities();
	Object getCredentials();
	Object getDetails();
	Object getPrincipal();
	boolean isAuthenticated();
	void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;

}
```
```java
public interface Principal {
    
    // 생략
    
    public String getName();
    
    // 생략
}

```

스프링 시큐리티에서 인증을 나타내는 계약

### 상속 계층 및 메서드
- Principal : java.security.Principal
  - 애플리케이션에 접근을 요청하는 사용자를 '주체'라고 한다.
  - `getName()` : 사용자의 이름 반환
- Authentication
  - `getCredentials()` : 사용자의 암호, 코드, 지문, 등 여러가지 비밀
  - `getAuthorities()` : 사용자의 권한들을 컬렉션으로 반환
  - `getDetails()` : 추가 세부 정보
  - `isAuthenticated()` : 인증 객체가 인증 됐거나(true), 인증 프로세스가 진행 중인지(false) 반환

---
