# Simple RPA (Robotic Process Automation)
![Commits Per Month](https://img.shields.io/github/commit-activity/m/satreci-admin/cnu-sw-academy-team-b/develop)
![Closed Pull Reqeusts](https://img.shields.io/github/issues-pr-closed/satreci-admin/cnu-sw-academy-team-b)
![Open Issues](https://img.shields.io/github/issues/satreci-admin/cnu-sw-academy-team-b?color=green)
<p align="center">
  <br>
  <img src="https://user-images.githubusercontent.com/57066971/200168121-c0e04e81-0dd3-4689-aa49-f99e9ad6a51a.gif">
  <br>
</p>

## 목차
1. [**프로젝트 개요**](#프로젝트-개요)
2. [**기술 스택**](#기술-스택)
3. [**구현 기능**](#구현-기능)
4. [**구현 화면**](#구현-화면)
5. [**ERD**](#ERD)
6. [**API 문서**](#API-)
7. [**브랜치 전략**](#브랜치-전략)
8. [**팀원**](#팀원)

## 프로젝트 개요
Simple RPA는 터미널에서 반복적으로 수행하던 작업(또는 작업 그룹)을 관리하고 실행하는 웹서비스입니다.

## 기술 스택
- **Server**
  - Spring Boot
  - Spring Data JPA
  - Spring Quartz
  - Jsch
- **DB**
  - MySQL
- **Test**
  - JUnit5
- **FE**
    - 사용자 페이지 : React
    - 관리자 페이지 : Thymeleaf

## 구현 기능
1. 작업명세서 관리
    - 리스트 조회
    - 상세 조회
    - 등록
    - 수정
    - 삭제
2. 로봇 관리
    - 리스트 조회
    - 상세 조회
    - 등록
    - 수정
    - 삭제
3. 작업 관리
    - 리스트 조회
    - 등록
    - 삭제
4. 작업명세서 실행
    - 즉시 실행
    - 예약 실행
        - 특정 날짜/시간에 1회 실행
        - 특정 날짜 이후로 매일 지정한 시간에 반복 실행

## 구현 화면
### 사이드바
![사이드바](https://user-images.githubusercontent.com/57066971/200170175-284e919e-8475-47d7-8dbe-126ab896fc7e.png)
### 로봇
![로봇](https://user-images.githubusercontent.com/57066971/200170248-c9025491-756d-46db-bae1-f7255ddaea74.png)
### 작업 명세서
![작업 명세서](https://user-images.githubusercontent.com/57066971/200170272-a92635c9-1be9-475f-81d9-de1b4564e520.png)

## ERD
![ERD](https://user-images.githubusercontent.com/57066971/200170534-efb9249c-d8b9-437e-b62d-a49f894fe95d.png)

## API 문서
[Postman API 문서](https://documenter.getpostman.com/view/15111130/2s83zgsPw4)

## 브랜치 전략
개인은 `feature/bugfix` 브랜치를 생성하여 개발 수행 후 `develop` 브랜치에 PR을 요청합니다.
- `main` : 배포 브랜치
- `develop` : 개발 통합 브랜치
- `feature/[BRANCH_NAME]` : 개인 작업 브랜치(신규기능 개발)
- `bugfix/[BRANCH_NAME]` : 개인 작업 브랜치(버그 수정)

## 팀원
<table>
  <tr>
    <td align="center" width="150px">
      <a href="https://github.com/Kim-AYoung" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/57066971?v=4" alt="김아영 프로필" />
      </a>
    </td>
    <td align="center" width="150px">
      <a href="https://github.com/sanggyunbak2856" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/73566382?v=4" alt="박상균 프로필" />
      </a>
    </td>
    <td align="center" width="150px">
      <a href="https://github.com/pum005" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/108645121?v=4" alt="최시환 프로필" />
      </a>
    </td>
    <td align="center" width="150px">
      <a href="https://github.com/hiih1600" target="_blank">
        <img src="https://avatars.githubusercontent.com/u/114333892?v=4" alt="최예진 프로필" />
      </a>
    </td>
  </tr>
  <tr> 
   <td align="center">
      <a href="https://github.com/Kim-AYoung" target="_blank">
        김아영
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/sanggyunbak2856" target="_blank">
        박상균
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/pum005" target="_blank">
        최시환
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/hiih1600" target="_blank">
        최예진
      </a>
    </td>
  </tr>
</table>

<!-- 
  ###프로젝트 구조
  - [TBD](./TBD) : TBD 프로젝트

  ###테스트 방법
  - junit5
  - intelliJ의 TestMe 플러그인 설치
    - test 하고 싶은 클래스 open
    - 'Code'-'TestMe' 혹은 Alt+Shift+Q 명령어 수행
    - Junit5 & Mockito 선택
    - 테스트 코드 템플릿 생성 확인 후 테스트 항목 수정하여 단위시험 코드 작성

  ### 빌드 방법
  - jar 파일 생성 (모든 프로젝트)
    - Gradle - aiw-engine - Tasks - build - bootJar
-->
