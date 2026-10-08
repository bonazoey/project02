# Oracle 연결 예제 적용 가이드

## 포함 파일

- [JDBC 의존성](../assets/gradle/oracle-dependency.gradle): 기존 Gradle dependencies에 병합한다.
- [환경 변수 기반 설정](../assets/resources/application.properties): 현재 설정의 관련 키만 교체한다.
- [공지사항 DDL](../assets/resources/database/schema.sql): Oracle 12c 이상, 신규 개발 스키마용이다.
- [Java 연결 확인](../assets/java/com/example/demo/tools/OracleConnectionCheck.java): Spring 없이 JDBC 연결을 분리 진단한다. 애플리케이션에 공개 진단 API를 만들지 않는다.

## Windows 실행 예시

프로젝트 루트 PowerShell에서 실행한다. 호스트·서비스·사용자는 실제 제공받은 값으로 바꾼다. 비밀번호는 프롬프트로 입력한다. 이 예시는 환경 변수 설정과 읽기 조회용이며 DDL을 실행하지 않는다.

```powershell
$env:ORACLE_JDBC_URL = 'jdbc:oracle:thin:@//localhost:1521/실제서비스명'
$env:ORACLE_USERNAME = '실제애플리케이션계정'
$oracleCredential = Get-Credential -UserName $env:ORACLE_USERNAME -Message 'Oracle 연결 계정'
$env:ORACLE_PASSWORD = $oracleCredential.GetNetworkCredential().Password
```

프로젝트 의존성 적용 후 해석된 JDBC 버전을 확인한다.

```powershell
.\gradlew.bat dependencyInsight --dependency ojdbc11 --configuration runtimeClasspath
```

아래 코드를 실행하기 전 `$oracleJdbcJar`를 실제 드라이버 JAR 절대 경로로 지정한다. Gradle 캐시 또는 조직에서 제공한 검증된 JAR을 사용한다. 임의의 다른 드라이버를 추가해 중복 로딩하지 않는다.

```powershell
$oracleJdbcJar = 'C:\실제드라이버폴더\ojdbc11.jar'
$oracleCheckOutput = Join-Path $env:TEMP ('oracle-check-' + [guid]::NewGuid())
New-Item -ItemType Directory -Path $oracleCheckOutput | Out-Null
javac -encoding UTF-8 -d $oracleCheckOutput .github/skills/spring-boot-oracle-setup/assets/java/com/example/demo/tools/OracleConnectionCheck.java
if ($LASTEXITCODE -ne 0) { throw 'Java 컴파일 실패' }
java -cp "$oracleCheckOutput;$oracleJdbcJar" com.example.demo.tools.OracleConnectionCheck
```

확인이 끝나면 사용하지 않는 비밀번호 환경 변수를 `Remove-Item Env:ORACLE_PASSWORD`로 제거한다. 애플리케이션을 같은 셸에서 실행하려면 실행 시점에 필요한 환경 변수를 유지하거나 다시 제공한다. IDE에서 실행하면 IDE 프로세스/실행 구성에도 환경 변수를 전달해야 한다.

연결 성공은 테이블 존재나 DML 권한을 보장하지 않는다. 애플리케이션 실행 전에 필요한 스키마가 준비되었는지 확인한다. 기본 예제는 초기화가 `never`라서 DDL을 실행하지 않는다. 새 개발 DB 자동 초기화가 명시적으로 필요하면 Oracle에 맞춘 schema.sql/data.sql만 지정하고 그 환경에서만 always를 사용한다. 재시작 때 CREATE TABLE 충돌이나 초기 데이터 중복이 발생하지 않도록 적용 방식을 정한다.

## 접속 오류 구분

| 현상 | 먼저 확인할 사항 |
| --- | --- |
| No suitable driver | 런타임 클래스패스와 JDBC URL |
| ORA-12541 | 호스트·포트·리스너와 네트워크 |
| ORA-12514 | 서비스 이름·PDB 상태·리스너 등록 |
| ORA-12505 | SID 형식과 리스너에 등록된 SID |
| ORA-01017 | 사용자·비밀번호·연결한 서비스/컨테이너 |
| ORA-28000 | 계정 잠금 상태; 반복 로그인 대신 관리자 확인 |
| ORA-00942 | 테이블·스키마·객체 권한; JDBC 연결 자체와 구별 |
| ORA-01031 | 요청 SQL에 필요한 권한; DBA 권한으로 우회하지 않음 |

클라우드 Wallet·TCPS 환경은 제공된 접속 설명자와 인증서 정책을 따른다. 일반 TCP 예제로 덮어쓰거나 인증서 검증을 끄지 않는다.

## 자료 구분

기존 PPT 43–56장, 특히 50장의 Oracle JDBC 설정과 예제 MyBatis 흐름을 바탕으로 작성했다. 원본의 외부 DB 주소·계정·비밀번호는 포함하지 않았다. 여기에 포함된 properties·공지사항 DDL·Java 진단 코드는 현재 프로젝트용으로 새로 작성한 예제이며 원본의 완성된 기능으로 설명하지 않는다.

## 공식 근거

- [Oracle JDBC URL](https://docs.oracle.com/en/database/oracle/oracle-database/21/jjdbc/data-sources-and-URLs.html): 서비스 이름 접속 형식.
- [Oracle JDBC 드라이버 배포 및 호환 정보](https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html): 드라이버 릴리스별 지원 JDK·DB 확인. JAR 이름만으로 호환성을 결정하지 않는다.
- [Spring Boot DB 초기화](https://docs.spring.io/spring-boot/how-to/data-initialization.html): SQL 초기화 모드와 스크립트 경로. 현재 프로젝트의 always 설정은 Oracle 전환 시 명시적으로 검토한다.
