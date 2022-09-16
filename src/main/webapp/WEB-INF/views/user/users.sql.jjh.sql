drop table users;
drop sequence seq_users_no;


create table users (
  no number,
  name varchar2(80),
  email varchar2(100) unique not null,
  password varchar2(20) not null,
  gender  varchar2(10),
  primary key(no)
);


create sequence seq_users_no
increment by 1
start with 1;

insert into users
values (seq_users_no.nextval, '정종욱', 'jongukjeong@gmail.com', '1234' , 'male');

insert into users
values (seq_users_no.nextval, '정우성', 'jungusung@gmail.com', '1234' , 'male');