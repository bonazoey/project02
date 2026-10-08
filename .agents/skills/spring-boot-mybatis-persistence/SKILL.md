---
name: spring-boot-mybatis-persistence
description: "H2 테이블·초기 데이터, MyBatis DAO·매퍼 XML·생성 키·동적 SQL을 추가하거나 수정할 때 사용한다."
---

# MyBatis와 H2 영속성

프로젝트 공통 규칙은 [AGENTS.md](../../../AGENTS.md)를 따른다.

## 구현 절차

1. 기존 스키마·데이터·엔티티·DAO·매퍼를 읽고 컬럼·키·null 허용 여부를 맞춘다. DB 엔티티에 HTTP 업로드용 MultipartFile을 넣지 않는다.
2. 스키마와 초기 데이터는 `src/main/resources/database/schema.sql`, `data.sql`에 둔다. H2 초기화 설정과 데이터 보존 요구를 확인한다.
3. dao의 *Dao.java에 @Mapper를 붙이고 메서드명과 XML statement id를 일치시킨다. namespace는 현재 DAO 전체 이름, 복수 인자는 @Param과 SQL 이름을 맞춘다.
4. 매퍼는 `src/main/resources/mybatis/mapper/*.xml`, 구성은 `src/main/resources/mybatis/mybatis_config.xml`을 사용한다. 별칭·resultType/resultMap·camelCase 설정을 실제 컬럼에 맞춘다.
5. 사용자 값은 `#{...}`로 바인딩한다. 수정·삭제에 식별 조건을 넣고 영향 행 수를 반환한다. 선택 필드는 if/set으로 제어한다.
6. 생성 키를 엔티티에 돌려주고 서비스 후속 조회에 사용한다. H2 스키마에 맞는 identity/generated keys 또는 sequence를 선택한다. Oracle dual·rownum·seq.nextval·sysdate를 그대로 복사하지 않는다.

## 검증

실제 H2에서 삽입·생성 키·필드 매핑·선택 수정·삭제 행 수를 확인한다. XML 파싱만으로 SQL을 검증했다고 하지 않는다. 스키마 변경 후 기존 공지사항 조회도 확인한다.

## 자료 근거

원본 동작과 차이를 확인할 때 [자료 대응과 적용 주의점](references/source-notes.md)을 읽는다.

## 포함된 예제 소스

이 SKILL의 `assets/source/java/`에 Java 예제와 관련 타입을 포함했다. XML·HTML이 필요한 기능에는 `assets/source/resources/`도 포함했다. [포함 파일 목록](references/source-notes.md#포함된-소스)에서 필요한 파일만 읽는다. 외부 back-end-spring-boot 프로젝트 경로를 찾을 필요가 없다. 원본 사본이므로 아래 참고 문서의 차이점과 이 SKILL의 구현 지침에 맞춰 적용하며, 그대로 실행 가능한 현재 프로젝트 코드로 간주하지 않는다.
