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

## JdbcUserDetailsManager 이용

### 설정
```groovy
	implementation 'org.springframework.boot:spring-boot-starter-data-jdbc'
	runtimeOnly 'com.h2database:h2'
```
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password:
    driver-class-name: org.h2.Driver

  h2:
    console:
      enabled: true
  sql:
    init:
      mode: always
```
- 인메모리 H2 데이터베이스 사용

```sql
CREATE TABLE IF NOT EXISTS `users` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(45) NULL,
    `password` VARCHAR(45) NULL,
    `enabled` INT NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `authorities` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(45) NULL,
    `authority` VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);
```
```sql
INSERT INTO `authorities` VALUES (NULL, 'user', 'write');
INSERT INTO `users` VALUES (NULL, 'user', '1111', '1');
```
- 테이블 생성, 기본 사용자 정보 등록

### 구성 클래스
```java

@Configuration
public class ProjectConfig {

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
```
- 기본 JdbcUserDetailsManager를 등록

### 실행
```shell
$ curl -u user:1111 http://localhost:8080/hello
Hello!
```
- 인증에 성공한다.

---
