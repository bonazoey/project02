---
name: spring-boot-jwt-auth
description: "JWT 발급·검증, Bearer 헤더, AccessTokenCheck 인터셉터와 인증된 사용자 전달을 구현할 때 사용한다."
---

# JWT 인증과 접근 제어

프로젝트 공통 규칙은 [AGENTS.md](../../../AGENTS.md)를 따른다.

## 구현 절차

1. 기존 인증 체계에 통합한다. 새 교육용 인터셉터는 JwtService, 런타임 메서드 어노테이션, HandlerInterceptor, config 등록으로 구성한다. 공개/보호 경로를 정한다.
2. 현재 빌드에 호환되는 JWT 의존성을 확인한다. 키·만료는 외부 설정으로 받으며 예제 키·장기 만료 상수를 복사하지 않는다. Duration 또는 long으로 시간을 계산한다.
3. 인증 성공 후 subject·최소 claim·만료를 넣어 서명한다. 서명 JWT는 암호화가 아니므로 payload에 비밀을 넣지 않는다.
4. Authorization의 Bearer 형식과 비어 있지 않은 토큰을 검사한다. 무조건 substring(7)을 호출하거나 쿼리 토큰 우회 경로를 복제하지 않는다.
5. 서명·만료 검증 결과에서만 claim을 읽고 재사용한다. 누락·빈 값·위조·만료·형식 오류·지원하지 않는 토큰을 일관된 인증 실패로 처리한다. subject가 없으면 통과시키지 않는다.
6. HandlerMethod 여부와 어노테이션을 확인하고 인증 사용자는 요청 범위에 보관한다. 인터셉터의 /database/** 대신 실제 보호 API 경로가 적용되는지 확인한다.
7. 신규 계약은 인증 실패 401, 권한 부족 403으로 구분하고 기존 오류 형식과 맞춘다. 유효한 토큰만으로 타인의 게시글·계좌 접근을 허용하지 않는다.
8. 필요한 CORS 사전 요청·정적 리소스 정책을 유지하고 새 엔드포인트의 보호 누락을 확인한다.

## 검증

정상·누락·빈 Bearer·짧은 헤더·위조·만료·형식 오류·subject 누락·공개/보호 경로를 검사한다. 실패 시 변경 컨트롤러가 실행되지 않는지 확인한다.

## 자료 근거

원본 동작과 차이를 확인할 때 [자료 대응과 적용 주의점](references/source-notes.md)을 읽는다.

## 포함된 예제 소스

이 SKILL의 `assets/source/java/`에 Java 예제와 관련 타입을 포함했다. XML·HTML이 필요한 기능에는 `assets/source/resources/`도 포함했다. [포함 파일 목록](references/source-notes.md#포함된-소스)에서 필요한 파일만 읽는다. 외부 back-end-spring-boot 프로젝트 경로를 찾을 필요가 없다. 원본 사본이므로 아래 참고 문서의 차이점과 이 SKILL의 구현 지침에 맞춰 적용하며, 그대로 실행 가능한 현재 프로젝트 코드로 간주하지 않는다.
