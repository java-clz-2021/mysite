--guestbook 테이블 삭제
drop table guestbook;

--guestbook 시퀀스 삭제
drop sequence seq_guestbook_no;

--guestbook 테이블 생성
create table guestbook (
    no number,
    name varchar2(80),
    password varchar2(20),
    content varchar2(2000),
    reg_date date,
    primary key(no)
);

--guestbook 시퀀스 생성
create sequence seq_guestbook_no
increment by 1
start with 1
nocache;


--insert 문
insert into guestbook
values(seq_guestbook_no.nextval, '정우성', '1234', '첫번째 게시글 입니다.', sysdate);

--select 문
select *
from guestbook;


rollback;
commit;