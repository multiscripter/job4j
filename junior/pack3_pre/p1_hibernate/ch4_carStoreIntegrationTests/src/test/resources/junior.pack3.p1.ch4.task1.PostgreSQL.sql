drop table if exists offers;
drop table if exists users;
drop table if exists cars_bodies;
drop table if exists cars;
drop table if exists brands;
drop table if exists bodies;
drop table if exists founders;

create table users (
    id serial not null primary key,
    name varchar (64) not null unique
);
insert into users (name) values ('testUser1');
insert into users (name) values ('testUser2');

create table bodies (
    id serial not null primary key,
    name varchar (16) not null unique
);
insert into bodies (name) values ('sedan');
insert into bodies (name) values ('hatchback');
insert into bodies (name) values ('universal');
insert into bodies (name) values ('crossover');
insert into bodies (name) values ('van');
insert into bodies (name) values ('off-road');

create table founders (
    id serial not null primary key,
    name_last varchar (32) not null unique,
    name varchar (32) not null unique
);
insert into founders (name_last, name) values ('Ford', 'Henry');
insert into founders (name_last, name) values ('Chevrolet', 'Louis');

create table brands (
    id serial not null primary key,
    name varchar (32) not null unique,
    founder_id int not null
);
insert into brands (name, founder_id) values ('Ford', 1);
insert into brands (name, founder_id) values ('Chevrolet', 2);

create table cars (
    id serial not null primary key,
    name varchar (32) not null unique,
    brand_id int not null
);
insert into cars (name, brand_id) values ('Focus', 1);
insert into cars (name, brand_id) values ('Fusion', 1);
insert into cars (name, brand_id) values ('Aveo', 2);
insert into cars (name, brand_id) values ('Niva', 2);

create table cars_bodies (
    car_id int not null,
    body_id int not null
);
insert into cars_bodies (car_id, body_id) values (1, 1);
insert into cars_bodies (car_id, body_id) values (1, 2);
insert into cars_bodies (car_id, body_id) values (2, 2);
insert into cars_bodies (car_id, body_id) values (3, 1);
insert into cars_bodies (car_id, body_id) values (4, 6);

create table offers (
    id serial not null primary key,
    user_id int not null,
    car_id int not null,
    body_id int not null,
    price int not null,
    status boolean not null
);
insert into offers (user_id, car_id, body_id, price, status) values (1, 1, 2, 100100, false);
insert into offers (user_id, car_id, body_id, price, status) values (1, 2, 1, 100200, false);
insert into offers (user_id, car_id, body_id, price, status) values (2, 4, 3, 100300, false);