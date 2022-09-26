# Simple RPA (Team B)

## Robotic Process Automation

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

### 브랜치 전략
개인은 feature/bugfix 브랜치를 생성하여 개발 수행 후 develop 브랜치에 PR을 요청합니다.
- main : 배포 브랜치
- develop : 개발 통합 브랜치
- feature/[BRANCH_NAME] : 개인 작업 브랜치(신규기능 개발)
- bugfix/[BRANCH_NAME] : 개인 작업 브랜치(버그 수정)