---
name: spring-boot-h2-setup
description: "Spring Boot·MyBatis의 H2 의존성, 메모리/파일 연결 모드, SQL 초기화, 개발용 콘솔과 연결 오류를 설정·진단할 때 사용한다. 일반 DAO·매퍼 작성은 mybatis-persistence skill 범위다."
---

# H2 데이터베이스 설정

프로젝트 공통 규칙은 [AGENTS.md](../../../AGENTS.md)를 따른다.

`build.gradle`, `application.properties`와 기존 초기화 SQL을 먼저 확인한다.

## 설정 절차

1. Gradle dependencies의 `runtimeOnly 'com.h2database:h2'`를 확인한다. Spring Boot가 관리하는 버전을 우선하고 필요 시 `./gradlew.bat dependencyInsight --dependency h2 --configuration runtimeClasspath`로 실제 버전을 확인한다. JDBC 연결만을 위해 JPA를 추가하지 않는다.
2. 요구에 맞춰 메모리 또는 파일 모드를 선택한다. 현재 앱은 `jdbc:h2:mem:demo;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`를 사용한다. 메모리 DB는 프로세스가 끝나면 사라지며 DB_CLOSE_DELAY=-1은 마지막 연결 종료 후 같은 JVM에서 유지하는 옵션이지 영구 저장 옵션이 아니다.
3. 재시작 후 데이터 보존이 필요할 때만 파일 URL로 바꾼다. 예: `jdbc:h2:file:./database/demo`. 상대 경로는 실행 작업 디렉터리 기준이므로 서비스/IDE 실행 위치를 확인하고 필요하면 절대 경로를 사용한다. .mv.db 확장자는 URL에 붙이지 않는다.
4. [설정 예제](assets/source/resources/application.properties)의 관련 키를 병합하고 전체 파일을 덮어쓰지 않는다. 현재 메모리 교육용 계정은 sa와 빈 비밀번호이며 이를 다른 배포 환경의 기본 인증 정책으로 일반화하지 않는다.
5. 초기화 위치를 `classpath:database/schema.sql`, `classpath:database/data.sql`로 명시한다. 현재 예제는 spring.sql.init.mode=always이며 시작할 때 schema 다음 data가 실행된다. 자동 초기화가 필요 없으면 never로 정한다.
6. [schema.sql](assets/source/resources/database/schema.sql)의 IF NOT EXISTS는 중복 테이블 생성을 피하지만 [data.sql](assets/source/resources/database/data.sql)의 반복 INSERT는 막지 않는다. 파일 모드에서는 초기화 1회 적용, 고유 키 기반 멱등 SQL 또는 별도 마이그레이션 중 요구에 맞는 방식을 정한다. 재시작마다 행이 증가하지 않는지 검증한다.
7. MyBatis의 config-location과 mapper-locations를 기존 경로로 유지한다. DAO의 namespace·statement id·엔티티 필드를 확인한다. Spring 자동 DataSource 구성을 우선하며 불필요한 수동 설정 클래스를 만들지 않는다.
8. 개발용 콘솔이 요청되면 [콘솔·진단 가이드](references/h2-guide.md)를 따른다. Spring Boot 4의 콘솔 모듈 요구를 확인하고 메모리 DB가 있는 앱 프로세스의 콘솔을 사용한다. 전체 보안을 비활성화하거나 외부 콘솔 접근을 기본 허용하지 않는다.

## 검증

- 실제 앱의 데이터와 분리된 메모리 DB에서 포함 SQL의 생성·초기 데이터·키 생성·커밋·롤백을 확인한다. [H2SetupCheck.java](assets/source/java/com/example/demo/tools/H2SetupCheck.java)는 이 목적의 독립 JDBC 예제다.
- 앱에 설정을 적용한 작업에서는 추가로 MyBatis 공지사항 조회와 홈 화면을 확인한다. 독립 JDBC 성공만으로 Spring/MyBatis 통합을 검증했다고 하지 않는다.
- 파일 모드는 임시 폴더에서 재연결·재시작 후 보존과 초기 데이터 중복 여부를 확인한다. 실제 DB 파일이나 잠금 파일을 해결책으로 삭제하지 않는다.
- Oracle/PostgreSQL 호환 모드는 해당 DB 전체 동작을 재현하지 않는다. H2 테스트 성공을 실제 대상 DB 검증으로 대신하지 않는다.

## 포함 자료

설정·SQL은 현재 프로젝트의 사본이며 외부 예제 프로젝트를 필요로 하지 않는다. Java 진단 코드는 이번 SKILL용으로 작성했다. [적용 차이·실행 방법·출처](references/h2-guide.md)를 필요한 경우 읽는다.
