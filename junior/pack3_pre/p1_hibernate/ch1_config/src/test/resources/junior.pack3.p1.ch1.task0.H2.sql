drop table if exists "comments";
drop table if exists "items";
drop table if exists "users";

create table "users" (
    id IDENTITY primary key,
    name varchar(64) not null,
    login varchar(64) not null,
    email varchar(128) not null unique,
    created timestamp not null,
    pass char(32) not null unique
);
insert into "users" (name, login, email, created, pass) values ('Путин', 'president', 'putin@kremlin.gov', '1952-10-07', 'ab788932cee4ff449d2ec584da8af2b7');
insert into "users" (name, login, email, created, pass) values ('Навальный', 'savior', 'naval@fbk.ru', '1976-06-04', '58634504e4d833e42588ecbc266085aa');
insert into "users" (name, login, email, created, pass) values ('Жирик', 'zhirik', 'zhirik@ldpr.ru', '1976-06-04', 'a4a4ff13a635bd92d6c69ada92ac2a90');

create table "items" (
    id IDENTITY primary key,
    user_id int not null,
	name varchar (128) not null,
	descr text not null,
	created timestamp not null
);
insert into "items" (name, user_id, descr, created) values ('Тестовая заявка 1', 1, 'Текст тестовой заявки 1', '2018-03-01 23:38:00');
insert into "items" (name, user_id, descr, created) values ('Тестовая заявка 2', 2, 'Текст тестовой заявки 2', '2018-03-02 23:39:00');
insert into "items" (name, user_id, descr, created) values ('Тестовая заявка 3', 3, 'Текст тестовой заявки 3', '2018-03-03 23:39:00');

create table "comments" (
    id IDENTITY primary key,
	item_id int not null,
    user_id int not null,
	comment text not null,
	created timestamp not null
);
insert into "comments" (item_id, user_id, comment, created) values (1, 1, 'Тест комментария к Тестовой заявке 1 от пользователя id 1', '2018-03-01 23:42:00');
insert into "comments" (item_id, user_id, comment, created) values (1, 2, 'Тест комментария к Тестовой заявке 1 от пользователя id 2', '2018-03-09 16:59:00');
insert into "comments" (item_id, user_id, comment, created) values (2, 2, 'Тест комментария к Тестовой заявке 2 от пользователя id 3', '2018-03-09 17:00:00');