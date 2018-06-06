drop table if exists items;

create table if not exists items (
    id serial primary key,
    item varchar (128) not null,
    descr text not null,
    created timestamp not null default now(),
    done bool not null default false
);
insert into items (item, descr, created, done) values ('Выполнить задание #49295 junior.pack3.p1.ch1.task2', 'Выполнить задание из видео #49295 Лямбды и шаблон wrapper.', '2018-04-12 15:59:00', true);