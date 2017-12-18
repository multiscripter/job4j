create table if not exists users (
    id serial primary key,
    name varchar(64) not null,
    login varchar(64) not null,
    email varchar(128) not null unique,
    createDate date not null,
    pass char(32) not null unique,
    role_id smallint not null references roles (id) on update cascade
);
create table if not exists roles (
    id smallserial primary key,
    name varchar(16) not null unique
);
/*
insert into users (name, login, email, createDate, pass, role_id) values ('Путин', 'president', 'putin@kremlin.gov', '1952-10-07', 'ab788932cee4ff449d2ec584da8af2b7', 1); pass: putin
insert into users (name, login, email, createDate, pass, role_id) values ('Навальный', 'savior', 'naval@fbk.ru', '1976-06-04', '58634504e4d833e42588ecbc266085aa', 2); pass: navalniy
insert into users (name, login, email, createDate, pass, role_id) values ('Жирик', 'zhirik', 'zhirik@ldpr.ru', '1976-06-04', 'a4a4ff13a635bd92d6c69ada92ac2a90', 2); pass: zhirik
update users set login = 'newlogin',email = 'new@email.ru',name = 'Новое имя',createdate = '2000-01-01' where id = 4;

insert into roles (name) values ('administrator'), ('user');
*/