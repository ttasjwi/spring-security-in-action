# SSIA
## Chapter 03
### Ex03. 사용자 관리에 LdapUserDetailsManager 이용

---

## LdapUserDetailsManager 이용

### 의존성
```groovy
dependencies {
    
    // 생략
    
	implementation 'org.springframework.security:spring-security-ldap'
	implementation 'com.unboundid:unboundid-ldapsdk'
}
```
- 위의 의존성을 추가한다

### 임베디드 LDAP 서버 설정
```ldif
dn: dc=springframework,dc=org
objectclass: top
objectclass: domain
objectclass: extensibleObject
dc: springframework

dn: ou=groups,dc=springframework,dc=org
objectclass: top
objectclass: organizationalUnit
ou: groups

dn: uid=john,ou=groups,dc=springframework,dc=org
objectclass: top
objectclass: person
objectclass: organizationalPerson
objectclass: inetOrgPerson
cn: John
sn: John
uid: john
userPassword: 12345
```
- 스프링부트 애플리케이션에 내장 임베디드 LDAP 서버를 설정한다.
- `src/mainresources/server.ldif` 에 LDAP LDIF 파일을 삽입
- 마지막에 디폴트 사용자를 추가

```yaml
spring:
  ldap:
    embedded:
      ldif: classpath:server.ldif
      base-dn: dc=springframework,dc=org
      port: 33389

```
- application.yml 에 임베디드 LDAP 서버에 대한 구성을 추가해야한다. 이 때 필요한 값은 다음과 같다.
  - LDIF 파일의 위치
  - 기본 도메인 주소 레이블 값
  - 서버의 포트

---

## Config
```java
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        var contextSource = new DefaultSpringSecurityContextSource(
                "ldap://127.0.0.1:33389/dc=springframework,dc=org"); // 컨텍스트 소스를 생성하여, LDAP 서버의 주소 지정

        contextSource.afterPropertiesSet();

        var manager = new LdapUserDetailsManager(contextSource); // LdapUserDetailsManager 인스턴스 생성

        // 사용자 이름 매퍼를 설정하고, manager에 사용자를 검색하는 방법 지시
        manager.setUsernameMapper(
                new DefaultLdapUsernameToDnMapper("ou=groups", "uid"));

        manager.setGroupSearchBase("ou=groups"); // 앱이 사용자를 검색하는데 필요한 그룹 검색 기준 설정

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
```
- 인증을 위한 LDAP 서버 구성을 완료했다면, 이를 이용하도록 애플리케이션을 구성할 수 있다.
- LDAP 서버를 통해 사용자를 인증하도록 LdapUserDetailsManager를 UserDetailsService 구현체로 등록하였다.

---
