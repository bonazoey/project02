---
name: spring-boot-openapi-docs
description: "구현된 API를 OpenAPI YAML·Postman Collection으로 문서화하고 요청·응답 예시를 검증할 때 사용한다."
---

# OpenAPI 명세와 API 검증 자료

프로젝트 공통 규칙은 [AGENTS.md](../../../AGENTS.md)를 따른다.

## 구현 절차

1. 실제 컨트롤러의 URL·메서드·바인딩·DTO·검증·인증·오류 응답을 조사한다. 없는 API를 구현된 기능처럼 문서화하지 않는다.
2. 사용하는 OpenAPI 버전을 확인하고 paths, parameters, requestBody, responses, schemas를 작성한다. 새 버전·문법은 공식 명세에서 확인한다.
3. JSON/폼/multipart, 파일·필수 필드, 페이지·다운로드 응답을 맞춘다. JWT 사용 시 Bearer security scheme과 공개/보호 operation을 구분한다.
4. 성공·검증·인증·권한·미존재·충돌 응답을 정의한다. 비밀번호·토큰 예시는 가상 값이나 환경 변수로 둔다.
5. Postman이 요청되면 기능별 폴더와 baseUrl/token 변수를 사용하는 Collection을 작성한다. 변환 결과를 소스·실제 응답과 비교한다. 외부 변환기에 자료를 자동 업로드하지 않는다.
6. Swagger UI도 요청되면 현재 규칙에 맞게 Thymeleaf MVC로 통합하고 초기화 JavaScript는 HTML 내부에 둔다. PPT의 정적 doc/index.html·별도 사용자 css/js 복사 절차를 그대로 따르지 않는다. 타사 배포물은 기존 자산 정책·호환성을 확인한다.
7. API 변경과 문서 수정을 함께 마무리하고 실제 검증과 예시만 작성한 부분을 구분한다.

## 검증

YAML/JSON 파싱, 경로 변수, schema 참조, 필드·상태 일치를 확인한다. 허용된 로컬 환경의 대표 요청과 비교한다. Collection 변환 성공만으로 정확성을 주장하지 않는다.

## 자료 근거

원본 동작과 차이를 확인할 때 [자료 대응과 적용 주의점](references/source-notes.md)을 읽는다.

## 포함된 예제 소스

이 SKILL의 `assets/source/java/`에 Java 예제와 관련 타입을 포함했다. XML·HTML이 필요한 기능에는 `assets/source/resources/`도 포함했다. [포함 파일 목록](references/source-notes.md#포함된-소스)에서 필요한 파일만 읽는다. 외부 back-end-spring-boot 프로젝트 경로를 찾을 필요가 없다. 원본 사본이므로 아래 참고 문서의 차이점과 이 SKILL의 구현 지침에 맞춰 적용하며, 그대로 실행 가능한 현재 프로젝트 코드로 간주하지 않는다.
