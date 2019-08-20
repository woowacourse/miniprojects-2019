insert into user (email,password,user_name) values ('a@naver.com','Aa1234!!','user');
insert into user (email,password,user_name) values ('b@naver.com','Aa1234!!','user2');

insert into article (contents, image_url, user) values ('moomin is moomin', '/uploads/2bdb598a-7cfe-4dce-9a73-8fb7364a3d09.jpg', 1);

insert into comment(contents, user_id, article_id) values ('내용',1,1);
insert into comment(contents, user_id, article_id) values ('contents2',1,1);