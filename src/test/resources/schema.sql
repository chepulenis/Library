drop table if exists books;
drop table if exists users;

create table users (
id serial not null primary key,
first_name varchar(20) not null,
last_name varchar(20) not null,
age integer not null
);

create table books (
id serial not null primary key,
title varchar(20) not null,
author varchar(20) not null,
ISBN varchar(20) unique not null,
is_taken boolean default false,
user_id integer references users(id)
);

commit;
