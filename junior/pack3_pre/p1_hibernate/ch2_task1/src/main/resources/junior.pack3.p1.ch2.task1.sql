drop table if exists gearboxes;
drop table if exists engines;
drop table if exists bodies;
drop table if exists cars;
drop table if exists cars_gearboxes;
drop table if exists cars_engines;
drop table if exists cars_bodies;

create table gearboxes (
    id serial not null primary key,
    name varchar (8) not null
);
insert into gearboxes (name) values ('manual');
insert into gearboxes (name) values ('auto');
insert into gearboxes (name) values ('robot');
insert into gearboxes (name) values ('variator');

create table engines (
    id serial not null primary key,
    name varchar (32) not null
);
insert into engines (name) values ('VAZ-11183-50');
insert into engines (name) values ('VAZ-11186');
insert into engines (name) values ('VAZ-11183');
insert into engines (name) values ('VAZ-21126');
insert into engines (name) values ('VAZ-21127');
insert into engines (name) values ('VAZ-21129');
insert into engines (name) values ('VAZ-21179');
insert into engines (name) values ('VAZ-2121');
insert into engines (name) values ('VAZ-11189');
insert into engines (name) values ('VAZ-21129');

create table bodies (
    id serial not null primary key,
    name varchar (16) not null
);
insert into bodies (name) values ('sedan');
insert into bodies (name) values ('hatchback');
insert into bodies (name) values ('universal');
insert into bodies (name) values ('crossover');
insert into bodies (name) values ('van');

create table cars (
    id serial not null primary key,
    name varchar (32) not null
);
insert into cars (name) values ('Kalina');
insert into cars (name) values ('Granta');
insert into cars (name) values ('Priora');
insert into cars (name) values ('Largus');

create table cars_gearboxes (
    car_id int not null,
    gearbox_id int not null
);
insert into cars_gearboxes (car_id, gearbox_id) values (1, 1);
insert into cars_gearboxes (car_id, gearbox_id) values (1, 2);
insert into cars_gearboxes (car_id, gearbox_id) values (2, 1);
insert into cars_gearboxes (car_id, gearbox_id) values (2, 3);
insert into cars_gearboxes (car_id, gearbox_id) values (3, 1);
insert into cars_gearboxes (car_id, gearbox_id) values (4, 1);

create table cars_engines (
    car_id int not null,
    engine_id int not null
);
insert into cars_engines (car_id, engine_id) values (1, 2);
insert into cars_engines (car_id, engine_id) values (1, 3);
insert into cars_engines (car_id, engine_id) values (1, 4);
insert into cars_engines (car_id, engine_id) values (1, 5);
insert into cars_engines (car_id, engine_id) values (2, 2);
insert into cars_engines (car_id, engine_id) values (2, 3);
insert into cars_engines (car_id, engine_id) values (2, 4);
insert into cars_engines (car_id, engine_id) values (2, 5);
insert into cars_engines (car_id, engine_id) values (3, 4);
insert into cars_engines (car_id, engine_id) values (3, 5);
insert into cars_engines (car_id, engine_id) values (4, 9);

create table cars_bodies (
    car_id int not null,
    body_id int not null
);
insert into cars_bodies (car_id, body_id) values (1, 2);
insert into cars_bodies (car_id, body_id) values (2, 1);
insert into cars_bodies (car_id, body_id) values (3, 1);
insert into cars_bodies (car_id, body_id) values (4, 3);
