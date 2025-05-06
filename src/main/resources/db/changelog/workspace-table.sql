--liquibase formatted sql


--changeset mrshoffen:2
CREATE TABLE IF NOT EXISTS workspaces
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name       VARCHAR(128) NOT NULL,

    is_public  BOOLEAN,

    created_at TIMESTAMP    NOT NULL,
    user_id    UUID         NOT NULL,
    UNIQUE (user_id, name)
);