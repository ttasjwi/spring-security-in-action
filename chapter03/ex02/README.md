# SSIA
## Chapter 03
### Ex02. UserDetailsManager, JdbcUserDetailsManager

---

## UserDetailsManager
```java
public interface UserDetailsManager extends UserDetailsService {

	void createUser(UserDetails user);
	void updateUser(UserDetails user);
	void deleteUser(String username);
	void changePassword(String oldPassword, String newPassword);
	boolean userExists(String username);
}
```
- UserDetailsService를 확장한 인터페이스
- 사용자 추가, 수정, 삭제, 비밀번호 변경, 사용자 존재 여부 확인
- 참고로, 2장에서 사용한 InMemoryUserDetailsService는 UserDetailsManager의 구현체였다.
  - 그래서 createUser를 통해 디폴트 사용자를 추가할 수 있었다.

---
