create table user (
    id integer not null auto_increment primary key,
    name varchar(50) not null,
    password varchar(50) not null,
    ssn varchar(20) not null,
    join_date timestamp
);