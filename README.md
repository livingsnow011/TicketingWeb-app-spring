# TicketingWebApplication

SeSAC-CloudMSA-BackendProject-Team-4

~~https://ticketingweb.ddns.net~~ 인스턴스 종료


# 목차
  - [요약](#요약)
  - [애플리케이션 아키텍처]
  - [ERD]
  - [패키지 구조]
  - [개발 환경](#개발-환경)
  - [사용 기술](#사용-기술)
    - [백엔드](#백엔드)
    - [프론트엔드](#프론트엔드)
    - [인프라](#인프라)
  - [프로젝트 목적](#프로젝트-목적)

## 요약
- 선착순이 아닌 추첨으로 이루어지는 공연 예매 웹 애플리케이션 개발
- 스프링 부트를 활용하여 기획 -> 개발 -> 배포 -> 운영까지 전과정 개발 경험 확보
- JPA를 사용한 도메인 설계 및 스프링 MVC 프레임워크 기반 백엔드 서버 구축 
- 3인 팀구성으로 협업 개발

<details>
  <summary> <h3>애플리케이션 아키텍처</h3></summary>
<div markdown="1">

![img](https://raw.githubusercontent.com/livingsnow011/TicketingWeb-app-spring/main/Architecture.png)

</div>
</details>
<details>
  <summary> <h3>api 명세서</h3></summary>
<div markdown="1">

| index | 상세 | HTTP method | uri | 관리자 권한 |
| --- | --- | --- | --- | --- |
| 1 | 메인 페이지 | GET | / |  |
| 2 | 공연 생성 페이지 | GET | /admin/show/new | o |
| 3 | 공연 생성 | POST | /admin/show/new | o |
| 4 | 공연 수정 페이지 | GET | /admin/show/{showId} | o |
| 5 | 공연 수정 | POST | /admin/show/{showId} | o |
| 6 | 공연 삭제 | DELETE | /admin/show/{showId} | o |
| 7 | 분류 별  조회+ 페이징 | GET | /show/{ShowClassification}+ /{page} |  |
| 8 | 공연 조회 | GET | /show/detail/{showId} |  |
| 9 | 회원 가입 페이지 | GET | /new |  |
| 10 | 회원 가입 | POST | /new |  |
| 11 | 로그인 페이지 | GET | /login |  |
| 12 | 로그인 에러 | GET | /login/error |  |
| 13 | 공연 예매 | POST | /book |  |
| 14 | 예매 공연 조회+ 페이징 | GET | /books+{page} |  |
</div>
</details>
<details>
  <summary> <h3>ERD</h3></summary>
<div markdown="1">
  
![img](https://raw.githubusercontent.com/livingsnow011/TicketingWeb-app-spring/main/ERD.png)
  
</div>
</details>
<details>
  <summary> <h3>패키지 구조</h3></summary>
<div markdown="1">
  
![img](https://raw.githubusercontent.com/livingsnow011/TicketingWeb-app-spring/main/Package.png)
  
</div>
</details>

## 개발 환경

- Trello 
- intelij
- Github
- Github Desktop
- mySQL workbench

## 사용 기술

### 백엔드

#### 주요 라이브러리 / 프레임워크
- Java openjdk version 11.0.2
- Spring Boot 2.7.1
- Spring Data JPA
- Spring Security
- Spring Cloud ( S3 공연 이미지 저장소 )

#### Build
- Gradle

#### database
- MySQL

### 프론트엔드
- HTML5/CSS/javascript
- Thymeleaf
- BootStrap 4.5.2
- JQuery ajax

### 인프라
- Amazon Linux 2 OS
- AWS EC2
- AWS RDS
- AWS S3
- AWS codedeploy
- Nginx
- Github Action

### 기타
- Lombok
- ModelMapper
- thymeleaf-extras-springsecurity
- thymeleaf-layout-dialect
- h2database

## 프로젝트 목적

## 맡은 역할 

## 기능 시연

### 공연 CRUD

### 스케줄러를 활용한 예매,추첨 과정

### 인증, 인가 및 관리자 페이지

### S3 이미지 저장소

### 페이징

### N + 1 문제

### CI/CD 무중단 배포 서비스 구축

### 도메인과 SSL 설정

## 후기 
