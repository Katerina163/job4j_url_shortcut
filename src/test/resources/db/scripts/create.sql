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
    path       varchar not null unique,
    code       varchar not null unique,
    count      int     not null,
    website_id int     not null references website (id)
);
--rollback drop table page;