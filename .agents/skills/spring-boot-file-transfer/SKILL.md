---
name: spring-boot-file-transfer
description: "multipart 업로드, 첨부 저장·교체, 파일 다운로드 헤더와 본문을 구현할 때 사용한다."
---

# 첨부 업로드와 다운로드

프로젝트 공통 규칙은 [AGENTS.md](../../../AGENTS.md)를 따른다.

## 구현 절차

1. 필드명·필수 여부·크기·형식 제한과 저장 방식을 정한다. 원본은 DB 바이트 저장이 활성화되어 있고 파일 시스템 저장은 주석 예제다.
2. 폼+파일은 @ModelAttribute, 분리 파트는 @RequestPart로 받는다. null/isEmpty를 처리하고 multipart 최대 파일·요청 크기를 properties에 설정한다.
3. 원본명·저장명·MIME·바이트를 구분한다. 작은 DB 파일은 byte[]/BLOB으로 매핑하고 큰 파일은 메모리와 스트리밍을 고려한다.
4. 파일 시스템을 사용하면 설정된 디렉터리와 서버 생성 이름을 사용한다. 사용자 파일명을 경로에 직접 연결하지 않고 경로 이탈을 차단한다. 스트림을 닫고 DB/파일 실패 시 정리 정책을 맞춘다.
5. 미첨부 수정은 보존, 새 첨부는 교체, 삭제 요청은 제거로 처리한다. 클라이언트 파일명·MIME만 믿고 실행/렌더링하지 않는다.
6. 다운로드는 게시글·첨부 존재와 권한을 확인한 후 Content-Type과 한글 파일명을 지원하는 Content-Disposition을 설정한다. 없는 첨부에 null 바이트를 쓰지 않는다.
7. 브라우저는 fetch와 FormData를 사용하고 Content-Type을 수동 지정해 boundary를 깨뜨리지 않는다. 인증 다운로드는 Authorization 헤더로 fetch한 blob을 사용하고 URL에 토큰을 넣지 않는다.

## 검증

미첨부·0바이트·한글명·크기 초과·잘못된 형식·권한 없음·바이트 동일성·교체/보존/삭제를 확인한다. 자료의 로컬 절대 경로를 남기지 않는다.

## 자료 근거

원본 동작과 차이를 확인할 때 [자료 대응과 적용 주의점](references/source-notes.md)을 읽는다.

## 포함된 예제 소스

이 SKILL의 `assets/source/java/`에 Java 예제와 관련 타입을 포함했다. XML·HTML이 필요한 기능에는 `assets/source/resources/`도 포함했다. [포함 파일 목록](references/source-notes.md#포함된-소스)에서 필요한 파일만 읽는다. 외부 back-end-spring-boot 프로젝트 경로를 찾을 필요가 없다. 원본 사본이므로 아래 참고 문서의 차이점과 이 SKILL의 구현 지침에 맞춰 적용하며, 그대로 실행 가능한 현재 프로젝트 코드로 간주하지 않는다.
