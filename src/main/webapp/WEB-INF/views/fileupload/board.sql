drop table board;
drop sequence seq_board_no;


CREATE TABLE board (
  no       NUMBER,
  title    VARCHAR2(500),
  content   VARCHAR2(4000),
  hit       NUMBER,
  reg_date  DATE,
  user_no   NUMBER,
  pos NUMBER,
  REF NUMBER,
  DEPTH NUMBER, 
  filename varchar2(100),
  filename2 varchar2(100),
  filesize number(11,0),
  filesize1 number(11,0),
  pass varchar2(15)
  
  PRIMARY KEY(no),
  CONSTRAINT c_board_fk FOREIGN KEY (user_no) 
  REFERENCES users(no)
);



CREATE SEQUENCE seq_board_no
INCREMENT BY 1 
START WITH 1 ;


insert into board
values (seq_board_no.nextval, '1번째 제목', '첫번째 내용', 0 , '2022-09-01', 1, 0, 0, 1, NULL, NULL, NULL, NULL, null);
insert into board
values (seq_board_no.nextval, '2번째 제목', '첫번째 내용', 0 , '2022-09-01', 1, 0, 0, 1, NULL, NULL, NULL, NULL, null);
insert into board
values (seq_board_no.nextval, '3번째 제목', '첫번째 내용', 0 , '2022-09-01', 1, 0, 0, 1, NULL, NULL, NULL, NULL, null);
insert into board
values (seq_board_no.nextval, '4번째 제목', '첫번째 내용', 0 , '2022-09-01', 1, 0, 0, 1, NULL, NULL, NULL, NULL, null);
insert into board
values (seq_board_no.nextval, '5번째 제목', '첫번째 내용', 0 , '2022-09-01', 1, 0, 0, 1, NULL, NULL, NULL, NULL, null);
insert into board
values (seq_board_no.nextval, '6번째 제목', '첫번째 내용', 0 , '2022-09-01', 1, 0, 0, 1, NULL, NULL, NULL, NULL, null);
insert into board
values (seq_board_no.nextval, '7번째 제목', '첫번째 내용', 0 , '2022-09-01', 1, 0, 0, 1, NULL, NULL, NULL, NULL, null);
insert into board
values (seq_board_no.nextval, '8번째 제목', '첫번째 내용', 0 , '2022-09-01', 1, 0, 0, 1, NULL, NULL, NULL, NULL, null);
insert into board
values (seq_board_no.nextval, '9번째 제목', '첫번째 내용', 0 , '2022-09-01', 1, 0, 0, 1, NULL, NULL, NULL, NULL, null);
insert into board
values (seq_board_no.nextval, '10번째 제목', '첫번째 내용', 0 , '2022-09-01', 1, 0, 0, 1, NULL, NULL, NULL, NULL, null);
insert into board
values (seq_board_no.nextval, '11번째 제목', '첫번째 내용', 0 , '2022-09-01', 1, 0, 0, 1, NULL, NULL, NULL, NULL, null);
