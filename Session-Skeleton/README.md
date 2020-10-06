# Session-Skeleton
`Spring-Session` 기반의 `LoginSkeleton`을 구현한 저장소 입니다.

## 설정
<details><summary> 세부 사항 </summary>

<br>

- `Session-Skeleton/src/main/resources` 하위에 `application.yml`생성 후 아래의 내용 삽입
```
spring:
  profiles:
    include: oauth
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  h2:
    console:
      enabled: true
      path: /h2-console
```

- `Session-Skeleton/src/main/resources` 하위에 `application-oauth.yml`생성 후 아래의 내용 삽입
```
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: {Google에서 발급된 Client-Id}
            client-secret: {Google에서 발급된 Client-Secret}
            scope: {Google에서 가져올 데이터 범위}
```
</details>