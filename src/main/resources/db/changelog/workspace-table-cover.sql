--liquibase formatted sql


--changeset mrshoffen:3
ALTER TABLE workspaces
    ADD COLUMN cover_url VARCHAR(255) DEFAULT NULL;