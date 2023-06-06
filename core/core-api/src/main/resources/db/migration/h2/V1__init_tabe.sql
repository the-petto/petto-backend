/* account 스키마 */
CREATE TABLE account (
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    account_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(127),
    password VARCHAR(255),
    token_weight BIGINT,
    nickname VARCHAR(127),
    activated BOOLEAN
);

/* authority 스키마 */
CREATE TABLE authority (
    authority_name VARCHAR(127) NOT NULL PRIMARY KEY
);

/* 다대다표현 account_authority 스키마 */
CREATE TABLE account_authority (
    account_id BIGINT NOT NULL,
    authority_name VARCHAR(127) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account (account_id),
    FOREIGN KEY (authority_name) REFERENCES authority (authority_name)
);

/* 초기 필수 권한 */
INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_MEMBER');
INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_ADMIN');

/* Board Content */
CREATE TABLE board_content (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    content TEXT NOT NULL
);

/* Board */
CREATE TABLE board (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    account_id BIGINT NOT NULL,
    board_content_id BIGINT NOT NULL,
    category VARCHAR(127) NOT NULL,
    title VARCHAR(511) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account (account_id),
    FOREIGN KEY (board_content_id) REFERENCES board_content (id)
);

/* Comment */
CREATE TABLE comment (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    board_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    FOREIGN KEY (board_id) REFERENCES board (id)
);

/* BoardImage */
CREATE TABLE board_image (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    board_id BIGINT NOT NULL,
    url VARCHAR(511) NOT NULL,
    FOREIGN KEY (board_id) REFERENCES board (id)
);