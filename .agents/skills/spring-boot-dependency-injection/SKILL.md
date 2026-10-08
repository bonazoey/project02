---
name: spring-boot-dependency-injection
description: "Bean 등록, 생성자 주입, 여러 구현체 선택, 설정값 주입이나 빈 연결 오류를 해결할 때 사용한다."
---

# Spring Bean과 의존성 주입

프로젝트 공통 규칙은 [AGENTS.md](../../../AGENTS.md)를 따른다.

## 구현 절차

1. 컴포넌트 스캔 범위와 현재 빈 연결 방식을 확인한다. 컨트롤러·서비스·매퍼 책임을 유지한다.
2. 서비스는 @Service, 보조 객체는 @Component, MyBatis 인터페이스는 @Mapper, 직접 생성할 객체는 config의 @Configuration/@Bean으로 등록한다.
3. 필수 의존성은 명시적인 생성자로 주입한다. 단일 생성자에는 @Autowired를 생략할 수 있다. 생성자는 직접 작성해 Lombok 사용 범위를 지킨다. 기존 필드 주입을 요청 없이 일괄 변경하지 않는다.
4. 동일 타입 빈이 여럿이면 @Qualifier에 실제 빈 이름을 지정한다. 일반 빈의 생성자 주입과 컨트롤러 요청 매개변수 해석을 혼동하지 않는다.
5. @Value의 키를 application.properties와 연결한다. 비밀 값은 외부 설정으로 전달하고 자료의 키·계정은 복사하지 않는다.
6. 싱글턴 빈 필드에 요청별 사용자나 변경 가능한 요청 데이터를 보관하지 않는다. 순환 의존은 책임 분리로 해결한다.

## 검증

컨텍스트 로딩으로 누락·중복·순환을 확인한다. 선택한 구현체가 호출되는지, 필수 설정 누락 원인이 드러나는지 검사한다.

## 자료 근거

원본 동작과 차이를 확인할 때 [자료 대응과 적용 주의점](references/source-notes.md)을 읽는다.

## 포함된 예제 소스

이 SKILL의 `assets/source/java/`에 Java 예제와 관련 타입을 포함했다. XML·HTML이 필요한 기능에는 `assets/source/resources/`도 포함했다. [포함 파일 목록](references/source-notes.md#포함된-소스)에서 필요한 파일만 읽는다. 외부 back-end-spring-boot 프로젝트 경로를 찾을 필요가 없다. 원본 사본이므로 아래 참고 문서의 차이점과 이 SKILL의 구현 지침에 맞춰 적용하며, 그대로 실행 가능한 현재 프로젝트 코드로 간주하지 않는다.
