create table if not exists orders (
	id smallserial not null primary key,
	name varchar (128) not null,
	descr text not null,
	created timestamp not null default now()
);