create type person as (
    firstname varchar(255),
    lastname varchar(255),
    phonenumber varchar(12),
    email varchar(255)
    );

create type bank_account as (
    account_number int8,
    balance int8
    );

create type bank_client as (
    client person,
    account bank_account
    );

create type bank_worker as (
    worker person,
    work_position varchar(255),
    code varchar(255)
    );

create type bank_type as (
    bank_name varchar(255),
    worker bank_worker,
    client bank_client
    );

create sequence bank_seq;

create table banks_type
(
    id   bigint default nextval('bank_seq'),
    bank bank_type,
    constraint banks_type_pk primary key (id)
);

select ((bank).worker).code from banks_type;

insert into banks_type (bank.bank_name,
                        bank.worker.worker.firstname,
                        bank.worker.worker.lastname,
                        bank.worker.worker.phonenumber,
                        bank.worker.worker.email,
                        bank.worker.work_position,
                        bank.worker.code,
                        bank.client.client.firstname,
                        bank.client.client.lastname,
                        bank.client.client.phonenumber,
                        bank.client.client.email,
                        bank.client.account.account_number,
                        bank.client.account.balance
                        )
values (
           'Сбербанк',
           'Работник',
           'Работник',
           '89008001212',
           'worker@mail.ru',
           'manager',
           '0101',
           'Клиент',
           'Клиент',
           '89008001212',
           'client@mail.ru',
           '10000000000001',
           '51023'
       );




