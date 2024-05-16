# Practice-Spring-Security-token

##### 사용 기술 : JPA, Spring-Security, JWT
##### 사용 디비 : H2 Database

---

### 토큰 기반 인증 - 구현 목록
- Provider 커스텀(AuthenticationManagement)
- 인증 방식 커스텀 & 사용자 인증 토큰 커스텀
- 로그인 이벤트 추적
  - 로그인 성공 및 실패 추적 모니터링
- 계층 권한 설정
- 멀티 테넌시 (사용하지는 않고 있습니다.)
- 미인증 & 미인가 접근 핸들러 조작

### 기타 구현 목록
- 사용 메모리 및 JVM 프로세스가 사용하는 CPU의 백분율
- 특정 이벤트에서 이메일 발송
