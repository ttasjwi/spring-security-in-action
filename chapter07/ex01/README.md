# SSIA
## Chapter 07 : 권한 부여 구성 - 액세스 제한

---

## GrantedAuthority
```java
public interface GrantedAuthority extends Serializable {
	String getAuthority();
}
```
- 사용자 권한을 나타내는 계약
- 하나의 UserDetails는 하나 이상의 권한을 가진다.
- 이 계약은 `getAuthority()` 메시지에 응답할 책임을 가지는데, String 형식으로 권한의 이름을 반환해야한다.

---
