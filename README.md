# TicketingWebApplication

SeSAC-CloudMSA-BackendProject-Team-4

~~https://ticketingweb.ddns.net~~ 인스턴스 종료

# 목차
  - [요약](#요약)
  - [개발 환경](#개발-환경)
  - [사용 기술](#사용-기술)
    - [백엔드](#백엔드)
    - [프론트엔드](#프론트엔드)
    - [인프라](#인프라)

## 요약
- 선착순이 아닌 추첨으로 이루어지는 공연 예매 웹 애플리케이션 개발
- 스프링 부트를 활용하여 기획 -> 개발 -> 배포 -> 운영까지 전과정 개발 경험 확보
- JPA를 사용한 도메인 설계 및 스프링 MVC 프레임워크 기반 백엔드 서버 구축 
- 3인 팀구성으로 협업 개발
### 애플리케이션 아키텍처
![img](https://raw.githubusercontent.com/livingsnow011/TicketingWeb-app-spring/main/Architecture.png)
### ERD
![img](https://raw.githubusercontent.com/livingsnow011/TicketingWeb-app-spring/main/ERD.png)
### 패키지 구조
![img](https://raw.githubusercontent.com/livingsnow011/TicketingWeb-app-spring/main/Package.png)

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
- Spring Cloud (S3 공연 이미지 저장소 )

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



