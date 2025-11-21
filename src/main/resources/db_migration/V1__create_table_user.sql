create table if not exists users(
    id varchar(36) primary key,
    username varchar(255) not null,
    name varchar(255),
    email varchar(255) not null,
    passwordHash varchar(255) not null,
)