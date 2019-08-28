insert into user(id, user_name, email, password, create_date, update_date, is_deleted ) values (1, 'robby', 'kangmin789@naver.com', 'P@ssW0rd', '2019-08-11 12:23:22', '2019-08-11 12:23:22', false);
insert into video(id, youtube_id, title, contents, creator_id, create_date, view_count) values(1, 'S8e1geEpnTA', '제목', '내용', 1, '2019-05-05 15:31:23', 100);
insert into comment(id, contents, create_date, update_date, author_id, video_id) values (1,'contents', '2019-05-05 15:31:23', '2019-05-05 15:31:23', 1, 1);
