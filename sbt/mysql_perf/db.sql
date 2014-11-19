drop database test_perf;
create database test_perf;
use test_perf;

drop table if exists cash;
create table cash(
    account_id bigint not null,
    cash decimal(31,11) not null,
    margin decimal(31,11) not null,
    payable decimal(31,11) not null,
    receivable decimal(31,11) not null,
    primary key (account_id)
) DEFAULT CHARACTER SET=utf8;

drop table if exists positions;
create table positions(
    id bigint not null auto_increment,
    account_id bigint not null,
    security_id bigint not null,
    quantity decimal(31,11) not null,
    carrying_value decimal(31,11) not null,
    primary key (id),
    index (account_id, security_id)
) DEFAULT CHARACTER SET=utf8;

drop table if exists transactions;
create table transactions(
    id bigint not null auto_increment,
    account_id bigint not null,
    security_id bigint not null,

    primary key (id)
) DEFAULT CHARACTER SET=utf8;