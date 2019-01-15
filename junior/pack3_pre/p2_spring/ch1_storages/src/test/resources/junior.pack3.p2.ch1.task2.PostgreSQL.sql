drop table if exists users;

create table users (
    id serial not null primary key,
    name varchar (64) not null unique
);
insert into users (name) values ('testUser1');
insert into users (name) values ('testUser2');