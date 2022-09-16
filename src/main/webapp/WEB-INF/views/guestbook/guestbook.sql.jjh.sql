DROP TABLE guestbook;
drop sequence seq_guestbook_no;


CREATE TABLE guestbook (
  no		NUMBER,
  name		VARCHAR2(80),
  password	VARCHAR2(20),
  content	VARCHAR(2000),
  reg_date	date,
  PRIMARY KEY(no)	
);


CREATE SEQUENCE seq_guestbook_no
INCREMENT BY 1 
START WITH 1 ;



insert into guestbook
values (seq_guestbook_no.nextval, 
        '정준하', 
        '1234', 
        '내용없음',
        sysdate
       );
      
insert into guestbook
values (seq_guestbook_no.nextval, 
        '하하', 
        '1234', 
        '내용없음',
        sysdate
       );

insert into guestbook
values (seq_guestbook_no.nextval, 
        '유재석', 
        '1234', 
        '내용없음',
        sysdate
       );