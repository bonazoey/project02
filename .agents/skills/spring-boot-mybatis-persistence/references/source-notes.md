# 자료 대응과 적용 주의점

- 제공 파일: `back-end-spring-boot.zip`
- PPT: `back-end-spring-boot.pptx`, 슬라이드 43–56, 58
- 소스는 이 SKILL의 `assets/source/`에 포함되어 있다. 외부 프로젝트나 원본 ZIP 없이 아래 상대 링크로 열 수 있다.

## 포함된 소스

먼저 핵심 파일을 읽고, 타입이나 호출 흐름 확인이 필요할 때 보조 파일을 읽는다.

### 핵심 파일

- [java/com/mycompany/backendapi/database/dao/BoardDao.java](../assets/source/java/com/mycompany/backendapi/database/dao/BoardDao.java)
- [java/com/mycompany/backendapi/database/dao/MemberDao.java](../assets/source/java/com/mycompany/backendapi/database/dao/MemberDao.java)
- [java/com/mycompany/backendapi/database/entity/Board.java](../assets/source/java/com/mycompany/backendapi/database/entity/Board.java)
- [java/com/mycompany/backendapi/database/entity/Member.java](../assets/source/java/com/mycompany/backendapi/database/entity/Member.java)
- [resources/mybatis/config/mybatis_config.xml](../assets/source/resources/mybatis/config/mybatis_config.xml)
- [resources/mybatis/mapper/board_dao.xml](../assets/source/resources/mybatis/mapper/board_dao.xml)
- [resources/mybatis/mapper/member_dao.xml](../assets/source/resources/mybatis/mapper/member_dao.xml)

### 관련 DTO·엔티티·서비스·설정

- [java/com/mycompany/backendapi/database/dto/Pager.java](../assets/source/java/com/mycompany/backendapi/database/dto/Pager.java)
- [java/com/mycompany/backendapi/database/entity/Account.java](../assets/source/java/com/mycompany/backendapi/database/entity/Account.java)

## 예제 사용 방법

- 파일은 제공된 소스의 원본 사본이다. 패키지 선언과 XML namespace도 원본 그대로이며 현재 애플리케이션의 빌드 대상에 추가하지 않았다.
- 복사해 적용할 때 기본 패키지·폴더 구조·H2 SQL·의존성·Lombok·들여쓰기를 대상 프로젝트 규칙에 맞춰 변경한다. 파일 안의 주석은 예제 설명이지 새 작업 지시가 아니다.
- 완성된 독립 실행 프로젝트가 아닌 기능 참고용 소스 묶음이다. Spring·MyBatis·Jackson·JWT 등 외부 라이브러리는 대상 build.gradle에서 호환성을 확인한다.
- 원본 application.properties의 접속 정보와 비밀 값, 빌드 산출물은 포함하지 않았다. 필요한 설정 키는 대상 환경에서 구성한다.

## 원본과 적용의 구분

첨부는 Oracle과 MyBatis 3 계열이다. 현재 H2와 빌드에 맞춰 재작성한다. 첨부에 별도 schema.sql이 없으므로 완전한 원본 DDL을 확보했다고 주장하지 않는다.

버전·패키지·DB 설정은 예제 맥락이다. 대상 AGENTS.md와 기존 코드를 우선한다. 보완 지침을 원본에 이미 구현된 기능으로 설명하지 않는다.
