create table if not exists users (
    id serial primary key,
    name varchar(64) not null,
    login varchar(64) not null,
    email varchar(128) not null unique,
    createDate date not null
);
/*
insert into users (name, login, email, createDate) values ('Путин', 'president', 'putin@mail.gov', '1952-10-07');
update users set login = 'newlogin',email = 'new@email.ru',name = 'Новое имя',createdate = '2000-01-01' where id = 4;
*/