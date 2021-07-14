--user 테이블 삭제
drop table users;

--user 시퀀스 삭제
drop sequence seq_user_no;

--user 테이블 생성
create table users (
    no number,
    id varchar2(20) unique not null,
    password varchar2(20) not null,
    name varchar2(20),
    gender varchar2(10),
    primary key(no)
);

--user 시퀀스 생성
create sequence seq_user_no
increment by 1
start with 1
nocache;


--insert 문
insert into users
values(seq_user_no.nextval, 'iremys', '1234', '황일영', 'male');

--select 문
select *
from users;

commit;
rollback;
