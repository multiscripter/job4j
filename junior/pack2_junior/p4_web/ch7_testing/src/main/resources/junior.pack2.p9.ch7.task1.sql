drop table if exists users;
drop table if exists roles;
create table roles (
    id smallserial primary key,
    name varchar(16) not null unique
);
insert into roles (name) values ('administrator');
insert into roles (name) values ('user');
create table users (
    id serial primary key,
    name varchar(64) not null,
    login varchar(64) not null,
    email varchar(128) not null unique,
    createDate date not null,
    pass char(32) not null unique,
    role_id smallint not null references roles (id)
);
insert into users (name, login, email, createDate, pass, role_id) values ('Путин', 'president', 'putin@kremlin.gov', '1952-10-07', 'ab788932cee4ff449d2ec584da8af2b7', 1);
insert into users (name, login, email, createDate, pass, role_id) values ('Навальный', 'savior', 'naval@fbk.ru', '1976-06-04', '58634504e4d833e42588ecbc266085aa', 2);
insert into users (name, login, email, createDate, pass, role_id) values ('Жирик', 'zhirik', 'zhirik@ldpr.ru', '1976-06-04', 'a4a4ff13a635bd92d6c69ada92ac2a90', 2);