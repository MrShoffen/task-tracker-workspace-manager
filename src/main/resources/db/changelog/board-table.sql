--liquibase formatted sql


--changeset mrshoffen:2
CREATE TABLE IF NOT EXISTS boards
(
    id         UUID PRIMARY KEY,
    name       VARCHAR(128) NOT NULL,

    created_at TIMESTAMP    NOT NULL,
    user_id    UUID         NOT NULL,
    UNIQUE (user_id, name)
);