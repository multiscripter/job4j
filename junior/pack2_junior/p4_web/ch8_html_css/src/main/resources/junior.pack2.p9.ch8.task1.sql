drop table users;
create table users (
    id serial primary key,
    name varchar(64) not null,
    login varchar(64) not null unique,
    email varchar(128) not null unique,
    createDate date not null,
    pass char(32) not null unique,
    role_id smallint not null/* references roles (id) on update cascade*/,
    countries_cities_id smallint not null/* references countries_cities (id) on update cascade*/
);
insert into users (name, login, email, createDate, pass, role_id, countries_cities_id) values ('Путин', 'president', 'putin@kremlin.gov', '1952-10-07', 'ab788932cee4ff449d2ec584da8af2b7', 1, 5);/* pass: putin */
insert into users (name, login, email, createDate, pass, role_id, countries_cities_id) values ('Навальный', 'savior', 'naval@fbk.ru', '1976-06-04', '58634504e4d833e42588ecbc266085aa', 2, 5);/* pass: navalniy */
insert into users (name, login, email, createDate, pass, role_id, countries_cities_id) values ('Богомаз', 'bogomaz', 'bogomaz@brjansk.ru', '1961-02-23', 'ef23f3d39bc58094ab15efc67f8a86ba', 2, 7);/* pass: bogomaz */
insert into users (name, login, email, createDate, pass, role_id, countries_cities_id) values ('Трамп', 'tramp', 'tramp@cash.us', '1946-01-20', '5322239adecab388474673aa3704cc24', 1, 3);/* pass: tramp */
insert into users (name, login, email, createDate, pass, role_id, countries_cities_id) values ('Жирик', 'zhirik', 'zhirik@ldpr.ru', '1946-01-20', 'a4a4ff13a635bd92d6c69ada92ac2a90', 2, 5);/* pass: zhirik */

drop table countries_cities;
create table countries_cities (
    id smallserial primary key,
    country_id smallint not null/* references countries (id) on update cascade*/,
    city_id smallint not null/* references cities (id) on update cascade*/
);
insert into countries_cities (country_id, city_id) values (1, 1), (1, 2), (1, 5), (1, 6), (2, 1), (2, 2), (2, 3), (2, 4);

drop table countries;
create table countries (
    id smallserial primary key,
    name varchar(32) not null unique
);
insert into countries (name) values ('США'), ('РФ');

drop table cities;
create table cities (
    id smallserial primary key,
    name varchar(32) not null unique
);
insert into cities (name) values ('Москва'), ('Санкт-Петербург'), ('Брянск'), ('Владивосток'), ('Вашингтон'), ('Майами');

drop table roles;
create table roles (
    id smallserial primary key,
    name varchar(16) not null unique
);
insert into roles (name) values ('administrator'), ('user');

/*
select users.id, users.name, users.login, users.email, users.createdate, users.pass, role_id, roles.name  as role_name, countries.id as country_id, countries.name as country_name, cities.id as city_id, cities.name as city_name from roles, countries, cities, countries_cities, users where role_id = roles.id and countries_cities_id = countries_cities.id and countries_cities.country_id = countries.id and countries_cities.city_id = cities.id and users.login = 'president';

insert into users (name, login, email, createDate, pass, role_id) values ('Путин', 'president', 'putin@kremlin.gov', '1952-10-07', 'ab788932cee4ff449d2ec584da8af2b7', 1); pass: putin
insert into users (name, login, email, createDate, pass, role_id) values ('Навальный', 'savior', 'naval@fbk.ru', '1976-06-04', '58634504e4d833e42588ecbc266085aa', 2); pass: navalniy
insert into users (name, login, email, createDate, pass, role_id) values ('Жирик', 'zhirik', 'zhirik@ldpr.ru', '1976-06-04', 'a4a4ff13a635bd92d6c69ada92ac2a90', 2); pass: zhirik
update users set login = 'newlogin',email = 'new@email.ru',name = 'Новое имя',createdate = '2000-01-01' where id = 4;

insert into roles (name) values ('administrator'), ('user');
*/