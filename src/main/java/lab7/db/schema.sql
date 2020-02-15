create table bank
(
    id   serial      not null,
    name varchar(60) not null,
    primary key (id)
);

create table client
(
    id          serial      not null,
    firstname   varchar(50) not null,
    lastname    varchar(50) not null,
    phonenumber varchar(20) not null,
    email       varchar(20),
    bank_id     bigint      not null,
    primary key (id),
    foreign key (bank_id) references bank (id) on delete cascade
);

create table worker
(
    id          serial      not null,
    firstname   varchar(50) not null,
    lastname    varchar(50) not null,
    phonenumber varchar(20) not null,
    email       varchar(20),
    position    varchar(20) not null,
    code        varchar(20) not null,
    bank_id     bigint      not null,
    primary key (id),
    foreign key (bank_id) references bank (id) on delete cascade
);

create table bankaccount
(
    id            serial not null,
    accountnumber bigint not null,
    balance       bigint not null,
    client_id     bigint not null,
    primary key (id),
    foreign key (client_id) references client (id) on delete cascade
);
