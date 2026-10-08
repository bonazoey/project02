---
name: spring-boot-response-contracts
description: "성공 응답의 DTO·노출 필드·직렬화·상태 코드·헤더와 JSON/XML 콘텐츠 협상을 변경할 때 사용한다. URL·요청 바인딩만 바꾸면 spring-boot-rest-endpoints, 입력값 제약은 spring-boot-validation, 예외의 오류 상태·본문 변환은 spring-boot-exception-handling을 사용한다."
---

# 응답 DTO와 콘텐츠 협상

프로젝트 공통 규칙은 [AGENTS.md](../../../AGENTS.md)를 따른다.

## 구현 절차

1. 요청 DTO, DB 엔티티, 응답 DTO를 구분하고 API 계약에 필요한 값만 매핑한다. 엔티티 전체를 그대로 노출하지 않는다.
2. 단일 객체는 DTO, 목록은 List, 목록과 페이지 정보는 명명된 DTO 또는 기존 Map 계약으로 반환한다. 상태·헤더가 필요하면 ResponseEntity를 사용한다.
3. 신규 계약은 생성 201, 조회 200, 본문 없는 성공 204 등 의미에 맞게 정한다. void 반환만으로 204가 설정된다고 가정하지 않는다. 기존 계약 변경 영향을 확인한다.
4. XML이 필요한 경우에만 현재 Jackson 세대와 호환되는 XML 변환기를 확인하고 produces와 Accept 협상을 구성한다. 자료의 Jackson 2 의존성을 고정 복사하지 않는다.
5. 양방향 DTO를 유지해야 한다면 `@JsonProperty(access = READ_ONLY)`는 응답만, `WRITE_ONLY`는 요청만 허용한다. 이 설정은 JSON 기준이며 폼 바인딩이나 권한 검사를 대신하지 않는다.
6. 비밀번호·해시·첨부 바이트·내부 저장 경로를 일반 응답에서 제외한다. 파일은 다운로드 엔드포인트로 제공한다.

## 검증

실제 JSON에서 제외 필드가 없는지, 서버 관리 필드의 입력이 반영되지 않는지 검사한다. XML 지원 시 JSON/XML 및 미지원 Accept를 확인한다. 204에는 본문을 넣지 않는다.

## 자료 근거

원본 동작과 차이를 확인할 때 [자료 대응과 적용 주의점](references/source-notes.md)을 읽는다.

## 포함된 예제 소스

이 SKILL의 `assets/source/java/`에 Java 예제와 관련 타입을 포함했다. XML·HTML이 필요한 기능에는 `assets/source/resources/`도 포함했다. [포함 파일 목록](references/source-notes.md#포함된-소스)에서 필요한 파일만 읽는다. 외부 back-end-spring-boot 프로젝트 경로를 찾을 필요가 없다. 원본 사본이므로 아래 참고 문서의 차이점과 이 SKILL의 구현 지침에 맞춰 적용하며, 그대로 실행 가능한 현재 프로젝트 코드로 간주하지 않는다.
