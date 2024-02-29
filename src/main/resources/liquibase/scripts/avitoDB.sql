-- liquibase formatted sql

-- changeset roma:1
CREATE TABLE users
(
    user_id          SERIAL PRIMARY KEY,
    email            VARCHAR(50) NOT NULL UNIQUE,
    encoded_password VARCHAR(255) NOT NULL,
    first_name       VARCHAR(50) NOT NULL,
    last_name        VARCHAR(50) NOT NULL,
    phone            VARCHAR(20) NOT NULL,
    role             VARCHAR(50) NOT NULL,
    image            TEXT
);
-- changeset ilya :2
CREATE TABLE ads
(
    ad_id       SERIAL PRIMARY KEY,
    author_id   INT  NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    image       TEXT NOT NULL,
    price       INT  NOT NULL,
    title       VARCHAR(100),
    description TEXT
);

-- changeset vyacheclav:3
CREATE TABLE comments
(
    comment_id    SERIAL PRIMARY KEY,
    ad_id         INT    NOT NULL REFERENCES ads (ad_id) ON DELETE CASCADE,
    author_id     INT    NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
    creating_time BIGINT NOT NULL,
    comment_text  TEXT   NOT NULL
);

