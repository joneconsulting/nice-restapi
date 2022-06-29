create table user (
    id integer not null auto_increment primary key,
    name varchar(255) not null,
    password varchar(255)  not null,
    ssn varchar(255) not null,
    join_date timestamp
);

create table post (
    id integer not null auto_increment primary key,
    description varchar(500) not null,
    user_id int not null
);