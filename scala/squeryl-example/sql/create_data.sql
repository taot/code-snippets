drop table if exists POSITION;
drop table if exists ACCOUNT;

create table ACCOUNT (
       ID bigint(20) not null,
       OWNER varchar(100) not null,
       CAPITAL decimal not null,
       primary key (ID)
);

create table POSITION (
       ID bigint(20) not null,
       ACCOUNT_ID bigint(20) not null,
       CAPITAL decimal not null,
       primary key (ID),
       foreign key (ACCOUNT_ID) references ACCOUNT (ID)
);

insert into ACCOUNT values (1, 'Terry Tao', 100000);
insert into ACCOUNT values (2, 'Hongsi Hu', 20000);
insert into ACCOUNT values (3, 'Chaoran Zheng', 2000);
insert into ACCOUNT values (4, 'Yanyan Shen', 2000);

insert into POSITION values (1, 1, 50000);
insert into POSITION values (2, 1, 100000);
insert into POSITION values (3, 2, 3000);
insert into POSITION values (4, 3, 50000);
