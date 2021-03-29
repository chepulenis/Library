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

insert into users (id, first_name, last_name, age) values
(1, 'Bob', 'Jordan', 21),
(2, 'Jennifer', 'May', 50),
(3, 'Robert', 'Smith', 26),
(4, 'Jennifer', 'Watson', 41);

insert into books (id, title, author, ISBN) values
(1, 'Romeo and Juliet', 'William Shakespeare', '978-0393090185'),
(2, 'Shining', 'Stephen King', '978-0393090186'),
(3, 'Don Quixote', 'Miguel de Cervantes', '978-0393090187'),
(4, 'Ulysses', 'James Joyce', '978-0393090188');

commit;
