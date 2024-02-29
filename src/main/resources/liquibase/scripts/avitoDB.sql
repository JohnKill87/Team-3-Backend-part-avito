--liquibase formatted sql

--changeset roma:1

CREATE TABLE users (
id SERIAL PRIMARY KEY,
email VARCHAR(255),
password VARCHAR(255),
first_name VARCHAR(255),
last_name VARCHAR(255),
phone VARCHAR(255),
role VARCHAR(255),
avatar VARCHAR(255)
);

--changeset ilya:2

CREATE TABLE ads (
pk INT PRIMARY KEY,
title VARCHAR(32),
image VARCHAR(255),
description VARCHAR(64),
price VARCHAR(8),
user_id INT,
FOREIGN KEY (user_id) REFERENCES users(id)
);


--changeset vyacheslav:3

CREATE TABLE comments (
pk INT PRIMARY KEY,
ad_pk INT,
text VARCHAR(64),
created_at BIGINT,
user_id INT,
FOREIGN KEY (ad_pk) REFERENCES ads(pk),
FOREIGN KEY (user_id) REFERENCES users(id))
