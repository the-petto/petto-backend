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
