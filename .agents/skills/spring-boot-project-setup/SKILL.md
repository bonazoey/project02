---
name: spring-boot-project-setup
description: 
    '신규 Spring Boot 프로젝트 생성시에만 홈 화면 및 공지사항 API 생성에 사용합니다. 
    기존 프로젝트의 일반적인 기능 수정이나 오류 수정에는 사용하지 않습니다.'
---

# Spring Boot 프로젝트 설정

프로젝트 공통 규칙은 [AGENTS.md](../../../AGENTS.md)를 따른다.

## 적용 범위
- 신규 Spring Boot 교육용 백엔드 프로젝트를 처음 구성할 때만 사용
- 기존 프로젝트에서는 사용자의 명시적 요청 없이 구조를 재편하거나 예제 API를 추가하지 않음

## 데이터베이스 설정
- notice 테이블 생성 및 초기 데이터 삽입
- MyBatis 매퍼 작성

## 공지사항 API
- `Notice` 엔티티 작성
- `NoticeController` → `NoticeService` → `NoticeDao` 조회 흐름을 구현
- `GET /api/notice-list`에서 데이터베이스의 공지사항 목록을 JSON으로 반환

## 홈 화면
- `MvcController`에서 `GET /` 요청에 `home.html` 템플릿을 반환
- 공지사항 API의 조회 결과를 화면에 표시
