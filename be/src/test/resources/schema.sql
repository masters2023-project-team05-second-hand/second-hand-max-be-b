DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS token;
DROP TABLE IF EXISTS sign_in_type;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS member_address;
DROP TABLE IF EXISTS product_image;
DROP TABLE IF EXISTS product_status;

CREATE TABLE product
(
    id                BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    seller_id         BIGINT        NOT NULL,
    status_id         INT           NOT NULL,
    address_id        BIGINT        NOT NULL,
    category_id       INT           NOT NULL,
    title             VARCHAR(50)   NOT NULL,
    content           TEXT          NOT NULL,
    price             BIGINT        NULL,
    created_time      TIMESTAMP     NOT NULL,
    thumbnail_img_url VARCHAR(1000) NOT NULL
);

CREATE TABLE token
(
    member_id     BIGINT        NOT NULL PRIMARY KEY,
    refresh_token VARCHAR(1000) NOT NULL
);

CREATE TABLE sign_in_type
(
    id       INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    provider VARCHAR(20) NOT NULL
);

CREATE TABLE member
(
    id              BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sign_in_type_id INT           NOT NULL,
    email           VARCHAR(256)  NOT NULL,
    nickname        VARCHAR(20)   NOT NULL,
    profile_img_url VARCHAR(1000) NOT NULL,
    created_time    TIMESTAMP     NOT NULL,
    INDEX idx_sign_in_type_id_email (sign_in_type_id, email)
);

CREATE TABLE category
(
    id      INT           NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(20)   NOT NULL,
    img_url VARCHAR(1000) NOT NULL
);

CREATE TABLE address
(
    id   BIGINT      NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE member_address
(
    id              BIGINT     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id       BIGINT     NOT NULL,
    address_id      BIGINT     NOT NULL
);

CREATE TABLE product_image
(
    id         BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT        NOT NULL,
    url        VARCHAR(1000) NOT NULL
);

CREATE TABLE product_status
(
    id   INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(10) NOT NULL
);
