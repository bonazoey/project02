# H2 적용과 오류 진단

## 포함 파일

- [application.properties](../assets/source/resources/application.properties): 현재 메모리 DB·SQL 초기화·MyBatis·컬러 로그 설정의 사본.
- [schema.sql](../assets/source/resources/database/schema.sql), [data.sql](../assets/source/resources/database/data.sql): 현재 공지사항 예제. 초기 INSERT는 재실행 시 중복된다.
- [H2SetupCheck.java](../assets/source/java/com/example/demo/tools/H2SetupCheck.java): 매 실행 고유한 메모리 DB를 생성해 SQL과 트랜잭션을 검증한다. 실제 앱 DB에 접속하지 않는다.

## 개발용 콘솔

현재 Spring Boot 4.1.1에서는 H2 JDBC 의존성만으로 콘솔 모듈이 포함된다고 가정하지 않는다. 콘솔이 요청되면 현재 BOM과 일치하는 `org.springframework.boot:spring-boot-h2console`을 개발 런타임 구성에 추가하고 해당 개발 환경에서만 아래 설정을 활성화한다.

```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.web-allow-others=false
```

실제 앱과 같은 JDBC URL·계정으로 연결한다. 별도로 실행한 H2 JAR 콘솔에 `jdbc:h2:mem:demo`를 입력하면 다른 프로세스의 별도 메모리 DB를 보게 된다. 콘솔 404는 모듈·설정·경로부터 확인하고 Security가 이미 있는 경우 콘솔에 필요한 제한된 설정만 검토한다.

## 독립 SQL 검증 실행

프로젝트 루트에서 실행하며 `$h2Jar`는 현재 Gradle에 해석된 실제 H2 JAR 절대 경로로 바꾼다. Java 코드가 H2 RunScript를 사용하므로 컴파일과 실행 모두 JAR이 필요하다.

```powershell
$h2Jar = 'C:\실제드라이버폴더\h2.jar'
$h2Skill = '.github/skills/spring-boot-h2-setup'
$h2CheckOutput = Join-Path $env:TEMP ('h2-check-' + [guid]::NewGuid())
New-Item -ItemType Directory -Path $h2CheckOutput | Out-Null
javac -encoding UTF-8 -cp $h2Jar -d $h2CheckOutput "$h2Skill/assets/source/java/com/example/demo/tools/H2SetupCheck.java"
if ($LASTEXITCODE -ne 0) { throw 'Java 컴파일 실패' }
java -cp "$h2CheckOutput;$h2Jar" com.example.demo.tools.H2SetupCheck "$h2Skill/assets/source/resources/database/schema.sql" "$h2Skill/assets/source/resources/database/data.sql"
```

## 오류 구분

| 현상 | 확인할 항목 |
| --- | --- |
| Driver not found | runtimeClasspath의 H2 의존성, 실제 실행 JDK |
| 테이블 없음·빈 DB | URL의 DB 이름·메모리/파일 모드, JVM 프로세스, 초기화 경로 |
| 파일 DB가 다른 위치에 생성 | 실행 작업 디렉터리와 절대 경로 |
| 초기화 SQL 실패 | 실제 H2 버전, 예약어·자료형·키 생성 문법; 오류 무시로 숨기지 않음 |
| 재시작 후 데이터 소실 | mem 모드의 정상 수명인지 확인 |
| 초기 데이터 중복 | 파일 모드와 always, 반복 INSERT |
| Database already in use | 다른 JVM/도구의 연결·잠금; 임의 파일 삭제 대신 정상 종료 확인 |
| 콘솔 로그인 후 다른 데이터 | 앱과 URL·계정·JVM이 일치하는지 확인 |

DB_CLOSE_ON_EXIT=FALSE는 JVM 종료 시 H2 자동 종료 훅을 비활성화하는 설정이며 메모리 데이터 영속성을 만들지 않는다. 파일 DB에서는 종료·백업·초기화 정책을 별도로 검토한다.

## 공식 근거

- [H2 연결 모드와 수명](https://h2database.com/html/features.html): 메모리·파일 URL과 DB_CLOSE_DELAY.
- [Spring Boot SQL 및 H2 콘솔](https://docs.spring.io/spring-boot/reference/data/sql.html): 콘솔 모듈과 개발 설정.
- [Spring Boot SQL 초기화](https://docs.spring.io/spring-boot/how-to/data-initialization.html): 초기화 모드·스크립트 경로.
