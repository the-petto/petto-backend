# petto-backend

# API 명세서
```
http://localhost:8080/docs/index.html
```
* `POST /api/v1/members`
    * 멤버를 생성한다.
* `PUT /api/v1/accounts/token`
  * JWT 토큰을 갱신한다.
* `POST /api/v1/accounts/token`
  * JWT 토큰을 발급한다.
* `DELETE /api/v1/accounts/{username}/token`
  * 해당 유저의 토큰을 만료시킨다.
* `GET /api/v1/boards/animal-walks`
  * 산책해주세요 게시글을 조회한다.
* `GET /api/v1/boards/animal-walks/{boardId}`
  * 산책해주세요 게시글을 조회한다.
* `POST /api/v1/boards/animal-walks`
  * 산책해주세요 게시글을 작성한다.
* `DELETE /api/v1/boards/{boardId}`
  * 게시글을 삭제한다.

# 시스템 아키텍처
![image](https://github.com/the-petto/petto-backend/assets/35598710/b6598ee5-fd73-4e45-98b0-bde441da3520)

# gradle build
```
./gradlew bootJar -p core/core-api
```

# java run
```
java -jar core/core-api/build/libs/core-api-0.0.1-SNAPSHOT.jar --server.port=8080
```