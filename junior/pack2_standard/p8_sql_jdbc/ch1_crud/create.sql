-- Database: job4j

create database job4j
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

create table if not exists statuses (
    id serial not null primary key,
    status varchar (16) not null
);
insert into statuses (status) values ('new');
insert into statuses (status) values ('in work');
insert into statuses (status) values ('closed');

create table if not exists categories (
    id serial not null primary key,
    category varchar (16) not null
);
insert into categories (category) values ('normal');
insert into categories (category) values ('urgent');

create table if not exists rights (
    id serial not null primary key,
    canAdd bool not null,
    canEdit bool not null,
    canDel bool not null
);
insert into rights (canAdd, canEdit, canDel) values (true, false, false);
insert into rights (canAdd, canEdit, canDel) values (true, true, false);
insert into rights (canAdd, canEdit, canDel) values (true, true, true);

create table if not exists roles (
    id serial not null primary key,
    rightsId int not null references rights(id),
    role varchar (16) not null
);
insert into roles (rightsId, role) values (1, 'user');
insert into roles (rightsId, role) values (2, 'moder');
insert into roles (rightsId, role) values (3, 'admin');

create table if not exists users (
    id serial not null primary key,
    name varchar (64) not null,
    regdate date not null default CURRENT_DATE,
    roleId int not null references roles(id)
);
insert into users (name, roleId) values ('Ivanov', 1);
insert into users (name, roleId) values ('Petrov', 2);
insert into users (name, roleId) values ('Sidorov', 3);
insert into users (name, roleId) values ('Smirnov', 1);

create table if not exists orders (
    id serial not null primary key,
    userId int not null references users(id),
    catId int not null references categories(id),
    message text not null,
    regdate timestamp not null default now(),
    statusId int not null references statuses(id) default 1
);
insert into orders (userId, catId, message) values (1, 1, 'Text content of first order.');
insert into orders (userId, catId, message) values (1, 1, 'Second order description.');
insert into orders (userId, catId, message) values (4, 1, 'Third order description.');
insert into orders (userId, catId, message) values (4, 2, 'four order description.');

create table if not exists comments (
    id serial not null primary key,
    orderId int not null references orders(id),
    userId int not null references users(id),
    comment text not null
);
insert into comments (orderId, userId, comment) values (1, 1, 'This is comment for order 1 from user Ivanov');

create table if not exists files (
    id serial not null primary key,
    orderId int not null references orders(id),
    file varchar (255) not null
);