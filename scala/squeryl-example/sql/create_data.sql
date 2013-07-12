drop table if exists ACCOUNT;
create table ACCOUNT (
       id bigint(20),
       owner varchar(100),
       capital decimal
);
insert into ACCOUNT values (1, 'Terry Tao', 100000);
insert into ACCOUNT values (2, 'Hongsi Hu', 20000);
insert into ACCOUNT values (3, 'Chaoran Zheng', 2000);
insert into ACCOUNT values (4, 'Yanyan Shen', 2000);
