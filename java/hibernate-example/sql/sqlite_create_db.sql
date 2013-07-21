drop table if exists ACCOUNT;

create table ACCOUNT (
       ID integer not null primary key autoincrement,
       OWNER varchar(100) not null,
       CAPITAL decimal not null
);

create table POSITION (
       ID integer not null primary key autoincrement,
       ACCOUNT_ID integer not null,
       TYPE varchar(20) not null,
       foreign key (ACCOUNT_ID) references ACCOUNT (ID)
);

create table CASH_POSITION (
       ID integer not null,
       CAPITAL decimal not null,
       primary key (ID),
       foreign key (ID) references POSITION (ID)
);

create table SEC_POSITION (
       ID integer not null,
       SECURITY_NAME varchar(100) not null,
       COST_BASE decimal not null,
       QUANTITY decimal not null,
       primary key (ID),
       foreign key (ID) references POSITION (ID)
);

insert into ACCOUNT values (1, 'Terry Tao', 100000);
insert into ACCOUNT values (2, 'Hongsi Hu', 20000);
insert into ACCOUNT values (3, 'Chaoran Zheng', 2000);
insert into ACCOUNT values (4, 'Yanyan Shen', 2000);
