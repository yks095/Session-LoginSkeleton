# Login Base Session
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) [![Build Status](https://travis-ci.org/yks095/Session-LoginSkeleton.svg?branch=master)](https://travis-ci.org/github/yks095/Session-LoginSkeleton) ![Coverage Status](https://coveralls.io/repos/github/yks095/Spring-LoginSkeleton/badge.svg?branch=master)

자주 사용되는 로그인, 회원가입 기능에 대한 베이스 코드를 작성한 뒤 향후 프로젝트에 적용하기 위해 만든 저장소입니다.

## Key Summary

1. `Spring MVC` + `Thymeleaf` 를 이용한 `MVC pattern` 웹 애플리케이션
2. `Spring Security` 를 이용한 세션기반 로그인, 회원가입
3. `Spring Security OAth2` 를 이용한 소셜 로그인 (`Google`), 회원가입
4. `Adapter pattern` 을 이용한 세션기반 회원가입 유저, OAuth2기반 회원가입 유저 통합관리
5. `Junit 5` 를 이용한 테스트 코드
6. `Travis CI` 를 이용한 CI(Continuous Integration)
7. `Coveralls` + `jacoco`  를 이용한 `coverage` 수치화

## Folder structure 

```
com
    └── kiseok
        └── sessionskeleton
            ├── SessionSkeletonApplication.java
            ├── AppRunner.java
            ├── account
            │   ├── Account.java
            │   ├── AccountAdapter.java
            │   ├── AccountController.java
            │   ├── AccountRepository.java
            │   ├── AuthAccount.java
            │   ├── AccountService.java
            │   ├── AccountRole.java
            │   ├── SocialType.java
            │   └── dto
            │       └── AccountDto.java
            └── config
                ├── BeanConfig.java
                ├── SecurityConfig.java
                └── auth
                    └── OAuthAttributes.java
```

## API 스펙

<details>

<div markdown="1">


| HTTP 메서드 |   요청 URL    | 인증 여부 |               응답                | HTTP 상태 |
| :---------: | :-----------: | :-------: | :-------------------------------: | :-------: |
|    `GET`    |   /sign-in    |     -     |    로그인 페이지를 반환합니다.    |   `200`   |
|    `GET`    |   /sign-up    |     -     |   회원가입 페이지를 반환합니다.   |   `200`   |
|    `GET`    | /sign-up-form |     -     |   회원가입 페이지를 반환합니다.   |   `200`   |
|    `GET`    |     /test     |     O     |    테스트 페이지를 반환합니다.    |   `200`   |
|    `GET`    |       /       |     O     |     메인 페이지를 반환합니다      |   `200`   |
|   `POST`    |   /sign-in    |     -     |  메인 페이지로 리다이렉션됩니다.  |   `302`   |
|   `POST`    |   /sign-up    |     -     |             아래 참고             |   `201`   |
|   `POST`    | /sign-up-form |     -     | 로그인 페이지로 리다이렉션됩니다. |   `302`   |

**POST /sign-in**

- `Request`  예시

  ~~~
  HTTP Method = POST
        Request URI = /sign-in
         Parameters = {email=[123@email.com], password=[password], _csrf=[6bad1c0a-50cb-445e-ab8a-b2caa8e9b4a9]}
  ~~~

- `Request Parameters` 설명

  | 파라미터명 |  자료형  |   설명    |
  | :--------: | :------: | :-------: |
  |  email  | `String` |  이메일   |
  |  password  | `String` | 비밀번호  |
  |   _csrf    | `String` | csrf 토큰 |

**POST /sign-up**

- `Request`  예시

  ~~~
  HTTP Method = POST
        Request URI = /sign-up
         Parameters = {_csrf=[e0cdabf5-9dce-4a1f-b1b6-c4cfbfc6b68f]}
            Headers = [Content-Type:"application/json;charset=UTF-8", Content-Length:"49"]
               Body = {"email":"email@email.com","password":"password"}
  ~~~

- `Request Parameters` 설명

  | 파라미터명 |  자료형  |   설명    |
  | :--------: | :------: | :-------: |
  |   _csrf    | `String` | csrf 토큰 |

- `Response` 예시 

  1.  `CREATED`
  
         ```
        Status = 201
         Error message = null
         Headers = [Content-Type:"text/plain;charset=UTF-8", Content-Length:"32", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0"]
         Content type = text/plain;charset=UTF-8
         Body = {}
        ```
  
  2. `BAD_REQUEST` 

     ```
     Status = 400
     Error message = null
     Headers = [Content-Type:"text/plain;charset=UTF-8", Content-Length:"18", X-Content-Type-Options:"nosniff", X-XSS-Protection:"1; mode=block", Cache-Control:"no-cache, no-store, max-age=0, must-revalidate", Pragma:"no-cache", Expires:"0"]
     Content type = text/plain;charset=UTF-8
     Body = {}
     ```

**POST /sign-up-form**

- `Request`  예시

  ~~~
  HTTP Method = POST
        Request URI = /sign-up-form
         Parameters = {email=[123@email.com], password=[password], _csrf=[9f529fe5-b50b-46e5-a93f-8c0b087c989e]}
  ~~~

- `Request Parameters` 설명

  | 파라미터명 |  자료형  |   설명    |
  | :--------: | :------: | :-------: |
  |  email  | `String` |  이메일   |
  |  password  | `String` | 비밀번호  |
  |   _csrf    | `String` | csrf 토큰 |

</div>

</details>

## DB 스키마

- 데이터베이스는 인-메모리 데이터베이스 `H2` 를 사용하였습니다.

- **Account**

  |   필드   |     타입     | NULL |   KEY   |
  | :------: | :----------: | :--: | :-----: |
  |    ID    |    BIGINT    |  NO  | PRIMARY |
  |  EMAIL   | VARCHAR(255) | YES  |    -    |
  |   NAME   | VARCHAR(255) | YES  |    -    |
  | PASSWORD | VARCHAR(255) | YES  |    -    |
  | PICTURE  | VARCHAR(255) | YES  |    -    |

## 실행방법

1. [구글 클라우드 플랫폼]() 에서 **클라이언트 ID** 와 **클라이언트 보안 비밀** 을 발급받는다.
2. `/src/main/resources/application-oauth.yml` 을 발급받은 `ID`와 `Secret` 으로 수정한다.
3. `IntelliJ` 를 이용한 실행
   1. File → New → Project From Version Control
   2. URL : https://github.com/yks095/Session-LoginSkeleton.git → CLONE
   3. Run → SessionSkeletonApplication.java
   4. Localhost:8080 입력
4. `Gradle`을 이용한 실행
   1. $ git clone https://github.com/yks095/Session-LoginSkeleton.git
   2. $ cd Spring-LoginSkeleton && cd Session-Skeleton
   3. $ ./gradlew test
   4. $ ./gradlew build
   5. $ ./gradlew bootrun
   6. Localhost:8080 입력

## 향후 추가 할 기능

- 토큰기반 `REST-API` 프로젝트에 추가 예정입니다.
  1. `JAVA SMTP` 를 이용한 `회원가입` 인증 
     - Account` 필드에 이메일 인증을 위한 `boolean` 변수가 추가 예정
  2. `Swagger` 를 이용한 API 문서 생성
  3. 소셜 로그인이 동작하는 것 처럼 테스트 코드 작성 (가능할까?)
  4. 테스트 코드를 꼼꼼하게 작성하여 `coverage` 올리기

## 개발환경

|     도구     |              버전               |
| :----------: | :-----------------------------: |
|    Spring    |    Spring Boot 2.3.4.RELEASE    |
|      OS      |            Mac OS X             |
|   개발 툴    | Intellij IDEA Ultimate 2020. 01 |
|     JDK      |              JDK 8              |
| 데이터베이스 |               H2                |
|   빌드 툴    |          gradle-6.6.1           |

## With

- [민경환](https://github.com/ber01)
- [임동훈](https://github.com/donghL-dev)
- [하상엽](https://github.com/ssayebee)
