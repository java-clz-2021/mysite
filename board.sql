drop table users;
drop sequence seq_users_no;

create table users (
    no number,
    id varchar2(20) unique not null,
    password varchar2(20) not null,
    name varchar2(20),
    gender varchar2(10),
    primary key(no)
);

create sequence seq_users_no
increment by 1
start with 1
nocache;

insert into users
values(seq_users_no.nextval,'aaa','1234','황일영','male');

insert into users
values(seq_users_no.nextval,'bbb','1234','이효리','female');

insert into users
values(seq_users_no.nextval,'ccc','1234','정우성','female');


insert into users
values(seq_users_no.nextval,'ddd','1234','유재석','female');


commit;

drop table board;
drop sequence seq_board_no;


CREATE TABLE board (
  no	    NUMBER,
  title 	VARCHAR2(500),
  content   VARCHAR2(4000),
  hit       NUMBER,
  reg_date  DATE,
  user_no   NUMBER,
  PRIMARY KEY(no),
  CONSTRAINT c_board_fk FOREIGN KEY (user_no) 
  REFERENCES users(no)
);


CREATE SEQUENCE seq_board_no
INCREMENT BY 1 
START WITH 1 
nocache ;

insert into board 
values (seq_board_no.nextval, '1번째 제목입니다.', '1번째 내용입니다.', 0, sysdate, 1);

insert into board 
values (seq_board_no.nextval, '2번째 제목입니다.', '2번째 내용입니다.', 0, sysdate, 2);

insert into board 
values (seq_board_no.nextval, '3번째 제목입니다.', '3번째 내용입니다.', 0, sysdate, 1);

insert into board 
values (seq_board_no.nextval, '4번째 제목입니다.', '4번째 내용입니다.', 0, sysdate, 2);

insert into board 
values (seq_board_no.nextval, '5번째 제목입니다.', '5첫번째 내용입니다.', 0, sysdate, 3);


insert into board 
values (seq_board_no.nextval, '6번째 제목입니다.', '6첫번째 내용입니다.', 0, sysdate, 3);

insert into board 
values (seq_board_no.nextval, '7번째 제목입니다.', '7첫번째 내용입니다.', 0, sysdate, 3);

insert into board 
values (seq_board_no.nextval, '8번째 제목입니다.', '8첫번째 내용입니다.', 0, sysdate, 1);


commit;

select * from 
board;


SELECT  BO.no,
        BO.title,
        BO.content,
        US.name,
        BO.hit,
        TO_CHAR(BO.reg_date, 'YYYY-MM-DD HH:MI') regDate,
        US.no userNo
FROM board BO, users US
WHERE  BO.user_no = US.no
and  BO.title like '%1%'					 
order by reg_date desc;
						 
test