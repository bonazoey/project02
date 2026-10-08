---
name: spring-boot-exception-handling
description: "입력 검증·도메인·DB 예외를 HTTP 오류 상태·헤더·공통 오류 본문으로 변환하거나 RestControllerAdvice·ExceptionHandler를 수정할 때 사용한다. 입력값 제약 자체는 spring-boot-validation, 성공 응답 DTO·직렬화는 spring-boot-response-contracts, URL·요청 바인딩은 spring-boot-rest-endpoints를 사용한다."
---

# REST 전역 예외 처리

프로젝트 공통 규칙은 [AGENTS.md](../../../AGENTS.md)를 따른다.

## 구현 절차

1. 기존 오류 필드·상태와 성공 계약을 확인한다. 원본 result/message는 참고 계약이다.
2. @RestControllerAdvice와 구체 @ExceptionHandler를 작성한다. basePackages는 현재 기본 패키지에 맞추고 MVC 화면 처리를 의도치 않게 바꾸지 않는다.
3. 입력 400, 인증 401, 권한 403, 미존재 404, 중복/상태 충돌 409 등 의미에 맞춘다. 기존 계약 변경 영향을 확인한다.
4. MethodArgumentNotValidException의 field/defaultMessage를 DTO/List로 반환하고 JSON 문자열로 이중 직렬화하지 않는다. 폼·경로·쿼리에서 실제 발생하는 오류도 확인한다.
5. DAO→Service→Controller 예외 전파로 롤백이 가능하게 한다. 구체 도메인 예외를 먼저 처리하고 예상 밖 오류에는 500과 일반 메시지를 반환한다.
6. 원인은 서버 로그에 남기되 비밀번호·토큰·SQL 내부 정보는 응답에 넣지 않는다. 모든 오류를 200으로 바꾸지 않는다.
7. 404 처리 때문에 정적 매핑을 전역으로 끄지 않는다. 현재 MVC의 미매핑/정적 리소스 오류 경로를 확인한다.

## 검증

입력 오류·미존재·중복·잔액 부족·예상 밖 예외의 상태와 본문을 검사한다. 홈·정적 이미지가 유지되고 실패 요청의 DB 변경이 남지 않는지 확인한다.

## 자료 근거

원본 동작과 차이를 확인할 때 [자료 대응과 적용 주의점](references/source-notes.md)을 읽는다.

## 포함된 예제 소스

이 SKILL의 `assets/source/java/`에 Java 예제와 관련 타입을 포함했다. XML·HTML이 필요한 기능에는 `assets/source/resources/`도 포함했다. [포함 파일 목록](references/source-notes.md#포함된-소스)에서 필요한 파일만 읽는다. 외부 back-end-spring-boot 프로젝트 경로를 찾을 필요가 없다. 원본 사본이므로 아래 참고 문서의 차이점과 이 SKILL의 구현 지침에 맞춰 적용하며, 그대로 실행 가능한 현재 프로젝트 코드로 간주하지 않는다.
