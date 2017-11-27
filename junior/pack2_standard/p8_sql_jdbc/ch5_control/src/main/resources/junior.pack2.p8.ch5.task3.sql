create table if not exists joboffers (
    id serial primary key,
    author varchar(64) not null,
    pubdate bigint not null,
    title varchar(128) not null,
    jobtext text not null,
    url varchar(512) unique not null
);
