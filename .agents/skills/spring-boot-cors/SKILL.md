---
name: spring-boot-cors
description: "다른 출처의 브라우저 API 호출에서 허용 출처·메서드·헤더와 OPTIONS 사전 요청을 설정하거나 문제를 해결할 때 사용한다."
---

# CORS와 사전 요청

프로젝트 공통 규칙은 [AGENTS.md](../../../AGENTS.md)를 따른다.

## 구현 절차

1. 실제 origin의 스킴·호스트·포트, API·메서드·헤더와 쿠키 사용을 확인한다. 같은 출처라면 추가 설정 필요성을 먼저 확인한다.
2. config의 WebMvcConfigurer.addCorsMappings에 필요한 API 범위·출처·메서드·헤더를 설정한다. 원본의 전역 wildcard를 환경 정책으로 복사하지 않는다.
3. 쿠키 인증에 필요한 경우 credentials와 구체 출처를 함께 설정한다. Bearer 헤더와 쿠키 전송을 혼동하지 않는다.
4. 유효한 OPTIONS가 토큰 부재로 차단되지 않게 인터셉터와 함께 확인한다. 기존 Security/필터와 중복 정책 충돌을 피한다.
5. 다운로드 파일명을 읽어야 하는 프런트엔드에는 필요한 응답 헤더만 노출한다. 출처는 배포 환경 설정으로 관리한다.

## 검증

허용/비허용 origin의 브라우저 요청, OPTIONS 요청 메서드·헤더, 실제 인증 요청을 검사한다. curl 성공만으로 브라우저 성공을 판단하지 않는다. CORS가 권한 검사를 대체하지 않는지 확인한다.

## 자료 근거

원본 동작과 차이를 확인할 때 [자료 대응과 적용 주의점](references/source-notes.md)을 읽는다.

## 포함된 예제 소스

이 SKILL의 `assets/source/java/`에 Java 예제와 관련 타입을 포함했다. XML·HTML이 필요한 기능에는 `assets/source/resources/`도 포함했다. [포함 파일 목록](references/source-notes.md#포함된-소스)에서 필요한 파일만 읽는다. 외부 back-end-spring-boot 프로젝트 경로를 찾을 필요가 없다. 원본 사본이므로 아래 참고 문서의 차이점과 이 SKILL의 구현 지침에 맞춰 적용하며, 그대로 실행 가능한 현재 프로젝트 코드로 간주하지 않는다.
