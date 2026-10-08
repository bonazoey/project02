---
name: spring-boot-pagination
description: "목록의 페이지·페이지 그룹·전체 건수 계산과 MyBatis 페이지 조회를 구현하거나 경계 오류를 수정할 때 사용한다."
---

# 목록 페이징

프로젝트 공통 규칙은 [AGENTS.md](../../../AGENTS.md)를 따른다.

## 구현 절차

1. pageNo 기본값, rowsPerPage, pagesPerGroup, 범위 초과 정책을 정한다. 원본 5행·5페이지는 예시다. 외부 페이지 크기 입력에는 상한을 둔다.
2. pageNo는 1 이상, 페이지·그룹 크기는 양수로 검증한다. count와 목록에 같은 검색 조건을 적용한다.
3. 전체 행 수가 양수일 때 다음 계산을 사용하며 큰 범위는 long으로 계산한다.

```text
totalPageNo = (totalRows + rowsPerPage - 1) / rowsPerPage
totalGroupNo = (totalPageNo + pagesPerGroup - 1) / pagesPerGroup
offset = (pageNo - 1) * rowsPerPage
groupNo = (pageNo - 1) / pagesPerGroup + 1
startPageNo = (groupNo - 1) * pagesPerGroup + 1
endPageNo = min(startPageNo + pagesPerGroup - 1, totalPageNo)
```

4. 0건이면 목록·pageArray는 빈 배열, 전체 페이지·그룹 수는 0으로 반환한다. pageNo 표현은 계약에 맞추고 가짜 링크를 만들지 않는다. 초과 페이지는 정한 정책에 따라 빈 목록 또는 보정 페이지를 반환한다.
5. H2 SQL은 안정적인 정렬과 `LIMIT #{rowsPerPage} OFFSET #{startRowIndex}`처럼 현재 DB에 맞는 문법을 사용한다. startRowIndex는 offset, startRowNo는 1 기반이다. 동률 정렬에는 ID를 추가한다.
6. 응답 pager와 목록 필드명을 클라이언트와 맞춘다.

## 검증

0건·1건·정확히 한 페이지·한 페이지+1건·그룹 경계·마지막·0/음수/초과 페이지를 확인한다. 인접 페이지의 중복·누락을 검사한다.

## 자료 근거

원본 동작과 차이를 확인할 때 [자료 대응과 적용 주의점](references/source-notes.md)을 읽는다.

## 포함된 예제 소스

이 SKILL의 `assets/source/java/`에 Java 예제와 관련 타입을 포함했다. XML·HTML이 필요한 기능에는 `assets/source/resources/`도 포함했다. [포함 파일 목록](references/source-notes.md#포함된-소스)에서 필요한 파일만 읽는다. 외부 back-end-spring-boot 프로젝트 경로를 찾을 필요가 없다. 원본 사본이므로 아래 참고 문서의 차이점과 이 SKILL의 구현 지침에 맞춰 적용하며, 그대로 실행 가능한 현재 프로젝트 코드로 간주하지 않는다.
