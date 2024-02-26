--liquibase formatted sql

--changeset roma:1

CREATE TABLE users (
id SERIAL PRIMARY KEY,
email VARCHAR(255),
first_name VARCHAR(255),
last_name VARCHAR(255),
phone VARCHAR(255),
role VARCHAR(255),
image VARCHAR(255)
);

--changeset ilya:2

CREATE TABLE ads (
id SERIAL PRIMARY KEY,
title VARCHAR(255),
description VARCHAR(255),
price INT,
user_id SERIAL,
image_id VARCHAR(255)
);

--changeset vyacheslav:3

CREATE TABLE comments (
id SERIAL PRIMARY KEY,
text VARCHAR(255),
createdAT DATE,
user_id SERIAL,
ad VARCHAR(255)
);

--changeset roma:4

ALTER TABLE users
ADD COLUMN password VARCHAR(255)

--changeset ilya:5

CREATE TABLE images (
image_id VARCHAR(255),
image BYTEA
);