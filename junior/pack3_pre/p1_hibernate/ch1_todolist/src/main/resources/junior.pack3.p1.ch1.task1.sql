drop table if exists todolist;

create table if not exists todolist (
    id serial primary key,
    item varchar (128) not null,
    descr text not null,
    created timestamp not null default now(),
    done bool not null default false
);
insert into todolist (item, descr, created, done) values ('Выполнить задание #6872 junior.pack3.p1.ch1.task0', 'Выполнить задание из видео #6872 junior.pack3.p1.ch1 Конфигурирование.', '2018-03-06 9:54:00', true);
insert into todolist (item, descr, created) values ('Выполнить задание #3786 junior.pack3.p1.ch1.task1', 'необходимо создать простое приложение todolist.
1. одна таблица в базе item. id, desc. created, done.
2. веб приложение должно иметь одну страницу index.html. 
3. все данные на форму загружаються через ajax.
4. в верху форма. добавить новое задание. описание.
5. список всех заданий. и галка выполено или нет.
6. вверху добавить галку. показать все. если включена. то показывать все. если нет. то только те что не выполены done = false.
7. данные должны сохраняться через hibernate, ', '2018-03-20 23:44:00');
insert into todolist (item, descr, created) values ('Купить хлеба', 'Купить хлеба на ярмарке.', '2018-03-20 23:53:00');
insert into todolist (item, descr) values ('Выполнить задание #2086 junior.pack3.p1.ch2.task1', 'Выполнить задание #2086 junior.pack3.p1.ch2.task1 Реализовать модели и связи для задание SQL хранилище машин.');