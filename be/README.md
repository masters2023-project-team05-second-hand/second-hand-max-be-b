![Last Update](https://img.shields.io/badge/Last_Upadate-2023--10--06-blue)

# 🥕 중고거래 팀 프로젝트 - Secondhand
- 당근 마켓을 모티브로 한 "중고 물품 거래 플랫폼"을 구현하는 팀 프로젝트
- 기간: 2023-08-21 ~ 2023-10-06 [6 Week]

<br/>

## 🧑🏻‍💻 팀원 소개

|                                   프론트엔드                                    |                                   프론트엔드                                    |                                    백엔드                                     |                                   백엔드                                    |
|:--------------------------------------------------------------------------:|:--------------------------------------------------------------------------:|:--------------------------------------------------------------------------:|:------------------------------------------------------------------------:|
| <img src = "https://avatars.githubusercontent.com/youzysu" width="180px;"> | <img src = "https://avatars.githubusercontent.com/aaaz425" width="180px;"> | <img src = "https://avatars.githubusercontent.com/jinny-l" width="180px;"> | <img src = "https://avatars.githubusercontent.com/swinb" width="180px;"> |
|                      [조이](https://github.com/youzysu)                      |                      [토코](https://github.com/aaaz425)                      |                    [Jinny](https://github.com/jinny-l)                     |                      [감귤](https://github.com/swinb)                      |


<br/>

## 🖥️ 동작 화면
https://github.com/eojjeogo-jeojjeogo/issue-tracker-max/assets/114852081/90edd514-1de7-418c-99c2-298fd1975bc5


<br/>

## 🪵 브랜치 전략
- BE Feature 작업은 `stage` 이하 브랜치에서 작업
- FE는 별도 [Repository](https://github.com/masters2023-project-team05-second-hand/second-hand-max-fe)에서 작업하고 있으며, 배포 시 `release-fe`로 Pull Request 받아서 Production 배포


| 브랜치                  | 설명                   |
|:---------------------|:---------------------|
| `release-production` | Production 서버 배포 브랜치 |
| `release-fe`         | FE 배포 브랜치            |
| `release-stage`      | BE 개발 서버 배포 브랜치      |
| `stage`              | BE 개발 브랜치            |


<br/>

## 📂 패키지 구조
- API 관련 모듈을 모아운 `api` 패키지와 전역에서 사용하는 모듈을 모아둔 `global` 패키지로 구성

```
. secondhand

  |____api
  | |____address   // 중고거래 동네 지역 도메인
  | |____category  // 중고 상품 카테고리 도메인
  | |____chat      // 채팅 도메인
  | |____image     // 중고상품 이미지 도메인
  | |____jwt       // JWT
  | |____member    // 회원 도메인
  | |____oauth     // 인증 도메인
  | |____product   // 중고 상품 도메인
  
  |____global      // 전역에서 사용하는 모듈
  | |____config
  | |____exception
  | |____filter
  | |____util
  
  |____SecondHandApplication.java
```

<br/>

## ⚙️ 개발 환경


### Back-End
![](https://img.shields.io/badge/Java-007396?style=flat&logo=OpenJDK&logoColor=white)
![](https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=SpringBoot&logoColor=white)
![](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=MySQL&logoColor=white)
![](https://img.shields.io/badge/GitHub_Actions-2088FF?style=flat&logo=githubactions&logoColor=white)

![](https://img.shields.io/badge/-NginX-269539?style=flat&amp;logo=Nginx&amp;logoColor=white)
![](https://img.shields.io/badge/-Docker-2496ED?style=flat&amp;logo=Docker&amp;logoColor=white)
![](https://img.shields.io/badge/AWS%20EC2-FA7343?style=flat&logo=amazonec2&logoColor=white)
![](https://img.shields.io/badge/-AWS_S3-569A31?style=flat&amp;logo=Amazon-S3&amp;logoColor=white)
![](https://img.shields.io/badge/AWS_RDS-527FFF?style=flat&logo=amazonrds&logoColor=white)
![](https://img.shields.io/badge/Redis-FF4D4D?style=flat&logo=redis&logoColor=white)

- Java: `JDK 11`
- SpringBoot: `ver. 2.7.14`
- MySQL: `ver. 8.0.33`
- Redis: `7.0.12`
- Amazon AWS: `EC2`,`RDS`

<br/>

## 🔧️ 인프라 구조

### Production

![이슈트래커 아키텍쳐 (1)](https://github.com/eojjeogo-jeojjeogo/issue-tracker-max/assets/108214590/11fdbd50-b925-4106-822f-e57b98b228c0)

### Stage

<br/>

## 💾 ERD
- [ERD cloud](https://www.erdcloud.com/d/mY7AM7XhTRcZ8HayK)

![image](https://github.com/masters2023-project-team05-second-hand/second-hand-max-be-b/assets/108214590/db19df12-e28a-4f2e-93f6-5e60b4d5f647)

<br/>

## 🌎 API 명세서
<a href="https://documenter.getpostman.com/view/28185148/2s9Y5TzkFu "><img src="https://img.shields.io/badge/Postman-FF6C37?style=flat&logo=postman&logoColor=white"></a>
