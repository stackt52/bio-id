CREATE SCHEMA IF NOT EXISTS auth;

CREATE TABLE IF NOT EXISTS auth.user
(
    id       UUID PRIMARY KEY,
    name     VARCHAR(250)        NOT NULL,
    email    VARCHAR(200) UNIQUE NOT NULL,
    password VARCHAR(200)        NOT NULL
);
