--liquibase formatted sql
--changeset Katerina163:1
insert into website(login, password, domain) values('b49a5e2c-75e2-4a1f-942c-4d0c1e25b25a', 'flwei9IF*Deri32', 'yandex.ru');
--rollback truncate table website;

--changeset Katerina163:2
insert into page(path, code, count, website_id) values('https://yandex.ru/pogoda/', 'YXkugmfM&S', 0, 1);
--rollback truncate table page;