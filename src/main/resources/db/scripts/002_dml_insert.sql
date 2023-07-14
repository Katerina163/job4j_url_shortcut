--liquibase formatted sql
--changeset Katerina163:1
insert into website(login, password, domain) values('KFod8dhe*Ed', 'flwei9IF*Deri32', 'google.com');
--rollback truncate table website;

--changeset Katerina163:2
insert into page(path, code, website_id) values('/page/12', 'dDKS8sh&S', 1);
--rollback truncate table page;