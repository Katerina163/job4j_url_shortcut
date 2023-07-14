--liquibase formatted sql
--changeset Katerina163:1
create table website (
    id       serial primary key,
    login    varchar unique not null,
    password varchar        not null,
    domain   varchar unique not null
);
--rollback drop table website;

--changeset Katerina163:2
create table page (
    id         serial primary key,
    path       varchar not null,
    code       varchar not null,
    website_id int     not null references website (id),
    unique (path, website_id)
);
--rollback drop table page;