# 프로젝트 개요

## 프로젝트 소개
- Spring Boot + Thymeleaf + MyBatis + H2 기반의 기본 웹 프로젝트

## 주요 기능
- `GET /`: Bootstrap 5.3 기반 공지사항 홈 화면
- `GET /api/notice-list`: 최신순 공지사항 JSON 목록

## 기술 구성
- Spring Boot 4.1.1
- Java 21
- Thymeleaf (JSP를 사용하지 않음)
- Gradle
- MyBatis Spring Boot Starter 4.1.0
- H2 Database
- JUnit과 AssertJ
- Lombok(@Data와 @Slf4j 두 개만 사용)

## 설치 및 설정
JDK 21을 설치한 뒤 Windows 환경 변수를 설정합니다.

- `JAVA_HOME`: JDK 21 설치 폴더로 설정합니다.
- `Path`: 기존 항목을 유지하고 `%JAVA_HOME%\bin`을 추가합니다. 

설정 후 PowerShell을 새로 열고 Java 버전이 21인지 확인합니다.

```powershell
java -version
javac -version
```

## 실행 방법
프로젝트 폴더에서 다음과 같이 실행합니다.

```powershell
.\gradlew.bat bootRun
```

실행 후 브라우저에서 http://localhost:8080 에 접속합니다.


