/* MySQL */
create database job4j_junior_pack2_p8_ch5_task2;

use job4j_junior_pack2_p8_ch5_task2;

create table if not exists company (
	id int not null primary key auto_increment,
    name varchar(64) not null
);

create table if not exists person (
    id int not null primary key auto_increment,
    name varchar(64) not null,
    surname varchar(64) not null,
    company_id int not null,
    
    foreign key (company_id) references company(id)
);

insert into company values (null, 'Microsoft'), (null, 'Google'), (null, 'Apple');
insert into person values (null, 'Bill', 'Gates', 1), (null, 'Michael', 'Abrash', 1), (null, 'Steve', 'Ballmer', 1);
insert into person values (null, 'Joshua', 'Bloch', 2), (null, 'Sergey', 'Brin', 2), (null, 'Guido', 'van Rossum', 2), (null, 'Amitabh Kumar', 'Singha', 2);
insert into person values (null, 'Steven Paul', 'Jobs', 3), (null, 'Timothy Donald', 'Cook', 3), (null, 'Stephen Gary', 'Wozniak', 3);

1.
select person.name, person.surname from person, company where person.company_id = company.id and company.id != 3;

2.
select company.name, count(person.company_id) as persons from company, person where company.id = person.company_id group by company.id order by persons desc limit 1;