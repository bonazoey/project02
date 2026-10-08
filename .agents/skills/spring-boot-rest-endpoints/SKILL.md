---
name: spring-boot-rest-endpoints
description: "Spring MVC REST API의 URL·HTTP 메서드·요청 매핑과 경로·쿼리·본문·폼·헤더·쿠키 바인딩을 추가·수정할 때 사용한다. 입력값 제약은 spring-boot-validation, 성공 응답 구조는 spring-boot-response-contracts, 오류 응답 변환은 spring-boot-exception-handling을 사용하며 함께 변경할 때만 병행한다."
---

# REST 엔드포인트와 요청 바인딩

프로젝트 공통 규칙은 [AGENTS.md](../../../AGENTS.md)를 따른다.

## 구현 절차

1. 기존 URL·메서드·필수 입력·Content-Type·응답을 확인한다. 신규 API는 현재 `/api` 관례를 따르고 기존 공개 경로를 임의 변경하지 않는다.
2. `@RestController`와 클래스 `@RequestMapping`을 사용한다. 조회·생성·수정·삭제에는 각각 `@GetMapping`, `@PostMapping`, `@PutMapping`/`@PatchMapping`, `@DeleteMapping`을 사용하고 전체/부분 수정 계약을 구별한다.
3. 입력 위치에 맞는 바인딩을 선택한다.

| 입력 | 방식 |
| --- | --- |
| 경로 식별자 | `@PathVariable("id")` |
| 검색·페이지 | `@RequestParam(value="pageNo", defaultValue="1")` |
| JSON 본문 | `@RequestBody` 요청 DTO |
| 폼·파일 포함 폼 | `@ModelAttribute` 요청 DTO |
| 개별 multipart 파트 | `@RequestPart("attach")` |
| 헤더·쿠키 | 이름과 필수 여부를 명시한 `@RequestHeader`, `@CookieValue` |

4. 바인딩 이름을 명시해 컴파일러의 매개변수 이름 보존 여부에 의존하지 않는다. `consumes`는 입력 형식, `produces`는 출력 형식에 맞춘다.
5. 요청 DTO를 서비스 입력으로 변환하고 DB 접근은 서비스에 위임한다. 비밀번호를 포함한 DTO 전체를 로그에 출력하지 않는다.

## 검증

정상 요청, 필수 값 누락, 숫자 변환 실패, 지원하지 않는 메서드·Content-Type, 선택 헤더·쿠키 누락을 확인한다. 실제 요청/응답 예시와 상태 코드를 남긴다.

## 자료 근거

원본 동작과 차이를 확인할 때 [자료 대응과 적용 주의점](references/source-notes.md)을 읽는다.

## 포함된 예제 소스

이 SKILL의 `assets/source/java/`에 Java 예제와 관련 타입을 포함했다. XML·HTML이 필요한 기능에는 `assets/source/resources/`도 포함했다. [포함 파일 목록](references/source-notes.md#포함된-소스)에서 필요한 파일만 읽는다. 외부 back-end-spring-boot 프로젝트 경로를 찾을 필요가 없다. 원본 사본이므로 아래 참고 문서의 차이점과 이 SKILL의 구현 지침에 맞춰 적용하며, 그대로 실행 가능한 현재 프로젝트 코드로 간주하지 않는다.
