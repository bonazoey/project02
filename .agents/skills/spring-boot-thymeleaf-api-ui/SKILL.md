---
name: spring-boot-thymeleaf-api-ui
description: "현재 프로젝트에서 Thymeleaf·Bootstrap 5.3 화면을 만들고 fetch로 REST API를 연결할 때 사용한다."
---

# Thymeleaf 화면과 fetch

프로젝트 공통 규칙은 [AGENTS.md](../../../AGENTS.md)를 따른다.

## 구현 절차

1. MvcController·home.html·API 계약을 읽는다. MVC는 템플릿 이름, API는 JSON을 반환하게 구분한다.
2. 화면은 templates/*.html, 이미지는 static/img에 둔다. MVC 경로는 기존 controller/MvcController.java에 통합한다.
3. Bootstrap 5.3 클래스로 화면을 작성한다. 지원하지 않는 스타일만 HTML 내부 style에 두고 JavaScript도 내부 script에 작성한다. 별도 css/js를 만들지 않는다.
4. 서버 값은 th:text, fetch 문자열은 textContent처럼 이스케이프되는 출력으로 표시한다. 사용자 값을 HTML로 직접 삽입하지 않는다.
5. JSON Content-Type·직렬화, FormData boundary 자동 처리, 필요한 인증 헤더를 구분한다. response.ok와 204/빈 본문을 처리한다.
6. 로딩·빈 목록·실패·성공을 표시하고 저장 중 중복 제출을 막는다. 페이지 버튼은 실제 pager 범위를 따른다.
7. 인증 다운로드는 헤더를 포함한 fetch→blob을 사용하고 객체 URL은 사용 후 해제한다. 토큰을 이미지 URL에 붙이지 않는다.

## 검증

브라우저에서 렌더링·정상 요청·빈 데이터·서버/검증 오류·중복 클릭·한글을 확인한다. 기존 홈과 /api/notice-list를 유지한다.

## 자료 근거

원본 동작과 차이를 확인할 때 [자료 대응과 적용 주의점](references/source-notes.md)을 읽는다.

## 포함된 예제 소스

이 SKILL의 `assets/source/java/`에 Java 예제와 관련 타입을 포함했다. XML·HTML이 필요한 기능에는 `assets/source/resources/`도 포함했다. [포함 파일 목록](references/source-notes.md#포함된-소스)에서 필요한 파일만 읽는다. 외부 back-end-spring-boot 프로젝트 경로를 찾을 필요가 없다. 원본 사본이므로 아래 참고 문서의 차이점과 이 SKILL의 구현 지침에 맞춰 적용하며, 그대로 실행 가능한 현재 프로젝트 코드로 간주하지 않는다.
