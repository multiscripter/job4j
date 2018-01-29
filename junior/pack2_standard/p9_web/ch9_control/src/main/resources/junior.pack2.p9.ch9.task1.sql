drop table if exists users;
drop table if exists users_musictypes;
drop table if exists musictypes;
drop table if exists addresses;
drop table if exists roles;

create table roles (
    id smallserial primary key,
    name varchar(16) not null unique
);
insert into roles (name) values ('administrator'), ('moderator'), ('user');

create table users_musictypes (
    id smallserial primary key,
    user_id smallint not null,
    musictype_id smallint not null
);
insert into users_musictypes (user_id, musictype_id) values (1, 1), (1, 2), (1, 3), (2, 1), (2, 2), (3, 1), (4, 3), (5, 3);

create table musictypes (
    id smallserial primary key,
    name varchar(32) not null unique
);
insert into musictypes (name) values ('rap'), ('rock'), ('pop');

create table addresses (
    id smallserial primary key,
    country varchar(64) not null,
    city varchar(64) not null,
    addr varchar(64) not null
);
insert into addresses (country, city, addr) values ('РФ', 'Москва', 'Кремль'), ('РФ', 'Москва', 'ФБК'), ('РФ', 'Брянск', 'Брянский лес'), ('США', 'Вашингтон', 'Капитолийский холм'), ('РФ', 'Москва', 'ЛДПР');

create table users (
    id smallserial primary key,
    login varchar(64) not null unique,
    pass char(32) not null unique,
    role_id smallint not null,
    addr_id smallint not null
);
insert into users (login, pass, role_id, addr_id) values ('president', 'ab788932cee4ff449d2ec584da8af2b7', 1, 1);/* pass: putin */
insert into users (login, pass, role_id, addr_id) values ('savior', '58634504e4d833e42588ecbc266085aa', 1, 2);/* pass: navalniy */
insert into users (login, pass, role_id, addr_id) values ('bogomaz', 'ef23f3d39bc58094ab15efc67f8a86ba', 2, 3);/* pass: bogomaz */
insert into users (login, pass, role_id, addr_id) values ('tramp', '5322239adecab388474673aa3704cc24', 2, 4);/* pass: tramp */
insert into users (login, pass, role_id, addr_id) values ('zhirik', 'a4a4ff13a635bd92d6c69ada92ac2a90', 3, 5);/* pass: zhirik */