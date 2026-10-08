---
name: spring-boot-validation
description: "요청 DTO의 필수 여부·길이·형식·범위·검증 그룹과 Jakarta Bean Validation 실행을 추가·수정할 때 사용한다. 입력을 받는 위치는 spring-boot-rest-endpoints, 검증 실패의 공통 HTTP 상태·오류 본문·전역 처리기는 spring-boot-exception-handling을 사용하며 검증 규칙과 함께 바뀔 때 병행한다."
---

# 요청 데이터 유효성 검사

프로젝트 공통 규칙은 [AGENTS.md](../../../AGENTS.md)를 따른다.

## 구현 절차

1. 요청별 필수 값·길이·형식·범위와 DB 제약을 확인한다. 예제의 아이디·비밀번호 길이 5–10자를 일반 규칙으로 강제하지 않는다.
2. 검증 의존성을 확인하고 필요할 때만 호환되는 validation starter를 추가한다. jakarta.validation을 사용한다.
3. 문자열 필수는 @NotBlank, 객체 필수는 @NotNull, 길이는 @Size, 이메일은 @Email, 패턴은 @Pattern, 숫자는 @Min/@Max 등으로 표현한다. 선택 필드를 구별한다.
4. `@Valid @RequestBody` 또는 `@Valid @ModelAttribute`로 실행하고 DTO 접근자와 생성 방식이 실제 바인딩에 맞는지 확인한다.
5. 가입/로그인 DTO를 분리한다. 같은 DTO에 다른 규칙이 필요할 때만 그룹과 `@Validated(그룹.class)`를 사용한다. 그룹 인터페이스만 선언해서는 동작이 달라지지 않는다.
6. 전역 처리기에서 입력 오류를 400과 필드별 오류 배열로 반환한다. 배열을 JSON 문자열로 다시 감싸지 않고 거부된 비밀번호 실제 값은 포함하지 않는다.

## 검증

정상 값, null, 빈 문자열, 공백, 경계 길이·숫자, 잘못된 이메일을 검사한다. 실패 시 저장 로직이 실행되지 않는지 확인한다. 중복 회원 등 DB 상태 검증은 서비스·DB 제약과 함께 처리한다.

## 자료 근거

원본 동작과 차이를 확인할 때 [자료 대응과 적용 주의점](references/source-notes.md)을 읽는다.

## 포함된 예제 소스

이 SKILL의 `assets/source/java/`에 Java 예제와 관련 타입을 포함했다. XML·HTML이 필요한 기능에는 `assets/source/resources/`도 포함했다. [포함 파일 목록](references/source-notes.md#포함된-소스)에서 필요한 파일만 읽는다. 외부 back-end-spring-boot 프로젝트 경로를 찾을 필요가 없다. 원본 사본이므로 아래 참고 문서의 차이점과 이 SKILL의 구현 지침에 맞춰 적용하며, 그대로 실행 가능한 현재 프로젝트 코드로 간주하지 않는다.
