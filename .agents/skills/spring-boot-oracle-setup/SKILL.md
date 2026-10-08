---
name: spring-boot-oracle-setup
description: "Spring Boot·MyBatis 프로젝트의 Oracle JDBC 연결, 환경 변수 설정, H2에서 Oracle로 전환할 때의 SQL 조정과 접속 오류 진단에 사용한다. Oracle 서버 설치나 데이터 이관 자체는 별도 요청 범위다."
---

# Oracle 데이터베이스 연결 설정

프로젝트 공통 규칙은 [AGENTS.md](../../../AGENTS.md)를 따른다.

`build.gradle`, `application.properties`, 스키마와 매퍼를 먼저 확인한다. Oracle 적용을 요청한 범위에서만 DB 설정을 변경한다. 포함된 예제의 접속 정보는 가상 값 또는 환경 변수다.

## 설정 절차

1. Oracle 서버 버전, 접속 호스트·포트, 서비스 이름 또는 SID, PDB 서비스, 애플리케이션 계정을 확인한다. 서버 설치가 필요하다면 OS·버전·설치 방식이 정해진 후 별도 범위로 다룬다. SYS/SYSTEM을 애플리케이션 기본 계정으로 사용하지 않는다.
2. 현재 BOM이 관리하는 JDBC 버전과 실제 해석된 의존성을 확인한다. [Gradle 예제](assets/gradle/oracle-dependency.gradle)를 기존 dependencies에 병합한다. `ojdbc11`이라는 이름만으로 Java 21 호환성을 판단하지 말고 선택한 드라이버 릴리스와 서버 호환성을 [공식 자료](references/oracle-guide.md#공식-근거)에서 확인한다.
3. [설정 예제](assets/resources/application.properties)를 기존 `src/main/resources/application.properties`에 필요한 키만 병합한다. 전체 파일을 덮어쓰지 않는다. 현재 H2 URL·계정과 `spring.sql.init.mode=always`를 그대로 남기지 않는다. Oracle 접속만 확인할 때는 자동 초기화를 `never`로 둔다.
4. 서비스 이름 URL은 `jdbc:oracle:thin:@//호스트:포트/서비스명`으로 작성한다. 기존 SID 접속은 `jdbc:oracle:thin:@호스트:포트:SID`로 구분한다. PDB 서비스 이름을 SID 자리에 넣지 않는다. URL 안에 비밀번호를 넣지 않는다.
5. 환경 변수 `ORACLE_JDBC_URL`, `ORACLE_USERNAME`, `ORACLE_PASSWORD`를 실행 프로세스에 제공한다. 비밀은 소스·문서·셸 명령 기록에 하드코딩하지 않는다. PowerShell 설정과 확인 방법은 [연결 가이드](references/oracle-guide.md)를 따른다.
6. 기존 스키마와 모든 매퍼의 H2 전용 문법을 검토한다. [Oracle 공지사항 DDL](assets/resources/database/schema.sql)은 Oracle 12c 이상용 참고 예제다. 기존 테이블이 있는 DB에 재실행하지 않는다. 데이터 이관이나 기존 데이터 삭제를 설정 작업에 포함시키지 않는다.
7. MyBatis 설정 경로는 `classpath:mybatis/mybatis_config.xml`, 매퍼는 `classpath:mybatis/mapper/*.xml`을 유지한다. 자동 DataSource 구성을 우선하며 연결만을 위해 별도 설정 클래스를 추가하지 않는다. MyBatis 프로젝트에 JPA ddl-auto 설정을 추가하지 않는다.

## SQL 전환 점검

| H2/기존 코드 | Oracle 적용 시 확인 |
| --- | --- |
| BIGINT | NUMBER(19), Java 타입 범위 확인 |
| VARCHAR | VARCHAR2와 BYTE/CHAR 길이 의미 확인 |
| 긴 본문 VARCHAR(5000) | CLOB 등 실제 길이와 문자셋에 맞춤 |
| IF NOT EXISTS | 대상 Oracle 버전 지원 여부 확인; 기존 존재 확인 후 DDL 실행 |
| identity/generated keys | 서버·드라이버·MyBatis 키 반환 동작 검증 |
| sequence | selectKey BEFORE에서 `SELECT 시퀀스.NEXTVAL FROM dual`, 키 속성 일치 |
| LIMIT/OFFSET | 12c 이상 OFFSET … ROWS FETCH NEXT … ROWS ONLY 또는 지원 버전용 rownum 쿼리 |
| boolean | 대상 버전의 SQL BOOLEAN 지원 및 기존 NUMBER(1) 매핑 확인 |
| 빈 문자열 | Oracle의 NULL 처리와 필수 값·검색 조건 검증 |

여러 DB를 계속 지원해야 하는 요청이면 설정 선택 방식과 DB별 매퍼/DDL 분리를 먼저 정한다. H2 Oracle 호환 모드 성공을 실제 Oracle 검증으로 대체하지 않는다.

## 연결 검증

1. [OracleConnectionCheck.java](assets/java/com/example/demo/tools/OracleConnectionCheck.java)를 JDK 21로 컴파일하고 JDBC 드라이버를 런타임 클래스패스에 넣어 실행한다. 계정·URL·비밀번호를 출력하지 않으며 `SELECT 1 FROM dual`만 조회한다.
2. 실제 JDBC 연결이 성공하면 애플리케이션을 실행해 MyBatis 조회와 한글·날짜·CLOB 매핑을 확인한다. DML이 요청된 경우에만 전용 테스트 데이터로 생성 키·수정·삭제·롤백을 확인한다.
3. DNS/TCP 연결, 로그인, SQL 조회, 애플리케이션 매핑을 단계별로 구분해 보고한다. 서버나 계정이 없으면 실제 연결 미검증이라고 명시한다.

## 파일 및 근거

[연결 가이드와 출처](references/oracle-guide.md)에 실행 예시, 접속 오류 분류, 원본 PPT와 새 예제의 구분을 정리했다. 접속 문제 또는 예제 적용 시 읽는다.
