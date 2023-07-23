--liquibase formatted sql
--changeset Katerina163:1
insert into website(login, password, domain) values('KFod8dhe*Ed', 'flwei9IF*Deri32', 'yandex.ru');
--rollback truncate table website;

--changeset Katerina163:2
insert into page(path, code, count, website_id) values('https://yandex.ru/pogoda/', 'YXkugmfMKy', 0, 1);
--rollback truncate table page;