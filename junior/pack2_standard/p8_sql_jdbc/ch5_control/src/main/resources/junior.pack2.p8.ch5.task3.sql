/* MySQL */
create database jpack2p8ch5task3;

create table jobs (
    id integer unique not null,
    thread varchar(128) not null,
    author varchar(64) not null,
    pubdate timestamp not null,
    jobtext text not null
);
