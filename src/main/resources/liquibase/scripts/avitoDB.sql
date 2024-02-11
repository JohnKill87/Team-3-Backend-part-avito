--liquibase formatted sql

--changeset roma:1

CREATE TABLE users (
id SERIAL PRIMARY KEY,
email VARCHAR(255),
firstName VARCHAR(255),
lastName VARCHAR(255),
phone VARCHAR(255),
role VARCHAR(255),
image VARCHAR(255)
);

--changeset ilya:2

CREATE TABLE ads (
id SERIAL PRIMARY KEY,
image VARCHAR(255),
title VARCHAR(255),
description VARCHAR(255),
price INT,
user_id SERIAL
);