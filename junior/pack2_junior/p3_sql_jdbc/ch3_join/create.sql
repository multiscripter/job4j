-- Database: job4j
-- Task: 3. Outer join

create table transmissionstypes (
    id serial not null primary key,
    name varchar (8) not null
);
insert into transmissionstypes (name) values ('manual');
insert into transmissionstypes (name) values ('auto');
insert into transmissionstypes (name) values ('robot');
insert into transmissionstypes (name) values ('variator');

create table engines (
    id serial not null primary key,
    name varchar (32) not null
);
insert into engines (name) values ('VAZ-11183-50');
insert into engines (name) values ('VAZ-11186');
insert into engines (name) values ('VAZ-11183');
insert into engines (name) values ('VAZ-21126');
insert into engines (name) values ('VAZ-21127');

create table corpses (
    id serial not null primary key,
    name varchar (16) not null
);
insert into corpses (name) values ('Sedan');
insert into corpses (name) values ('Hatch');

create table cars (
    id serial not null primary key,
    name varchar (32) not null
);
insert into cars (name) values ('Kalina');
insert into cars (name) values ('Granta');
insert into cars (name) values ('Priora');

create table cars_transmissionstypes (
    carid int not null,
    transmissionstypeid int not null
);
insert into cars_transmissionstypes (carid, transmissionstypeid) values (1, 1);
insert into cars_transmissionstypes (carid, transmissionstypeid) values (1, 2);
insert into cars_transmissionstypes (carid, transmissionstypeid) values (2, 1);
insert into cars_transmissionstypes (carid, transmissionstypeid) values (2, 3);
insert into cars_transmissionstypes (carid, transmissionstypeid) values (3, 1);

create table cars_engines (
    carid int not null,
    engineid int not null
);
insert into cars_engines (carid, engineid) values (1, 2);
insert into cars_engines (carid, engineid) values (1, 3);
insert into cars_engines (carid, engineid) values (1, 4);
insert into cars_engines (carid, engineid) values (1, 5);
insert into cars_engines (carid, engineid) values (2, 2);
insert into cars_engines (carid, engineid) values (2, 3);
insert into cars_engines (carid, engineid) values (2, 4);
insert into cars_engines (carid, engineid) values (2, 5);
insert into cars_engines (carid, engineid) values (3, 4);
insert into cars_engines (carid, engineid) values (3, 5);

create table cars_corpses (
    carid int not null,
    corpsid int not null
);
insert into cars_corpses (carid, corpsid) values (1, 2);
insert into cars_corpses (carid, corpsid) values (2, 1);
insert into cars_corpses (carid, corpsid) values (3, 1);
