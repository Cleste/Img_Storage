create sequence hibernate_sequence start 1 increment 1;

create table images (
name varchar(2048) not null,
primary key (name)
);