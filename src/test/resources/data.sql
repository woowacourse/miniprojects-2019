INSERT INTO user (id, email, last_name, first_name, password)
VALUES (1, 'ddu0422@naver.com', 'lee', 'mir', 'asdf1234!A');

INSERT INTO user (id, email, last_name, first_name, password)
VALUES (2, 'eara12sa@naver.com', 'lee', 'abc', 'asdf1234!A');

INSERT INTO user (id, email, last_name, first_name, password)
VALUES (999, 'andole@naver.com', 'lee', 'abc', 'asdf1234!A');


INSERT INTO user (id, email, last_name, first_name, password)
VALUES (998, 'test1@naver.com', 'last', 'first', 'asdf1234!A');

INSERT INTO user (id, email, last_name, first_name, password)
VALUES (997, 'test2@naver.com', 'lastt', 'firstt', 'asdf1234!A');


INSERT INTO article (id, updated_time, contents, image_url, video_url, open_range, author_id)
VALUES (1, '2019-08-28 23:40:30', 'SunBook Contents', 'https://file.namu.moe/file/105db7e730e1402c09dcf2b281232df017f0966ba63375176cb0886869b81bf206145de5a7a149a987d6aae2d5230afaae4ca2bf0b418241957942ad4f4a08c8'
, 'https://youtu.be/mw5VIEIvuMI', 'ALL', 1);

INSERT INTO article (id, updated_time, contents, image_url, video_url, open_range, author_id)
VALUES (2, '2019-08-28 23:41:30', 'Update Contents', 'http://mblogthumb2.phinf.naver.net/MjAxNzA2MDhfODYg/MDAxNDk2ODgyNDE3NDYz.yMs2-E3-GlBu9U_4r2GMnBd1IEgVlWG2Qos9pb-2WWIg.M4JN5W9K2kMt9n76gjYQUKPBGt0eHMXE0UrvWFvr6Vgg.PNG.smartbaedal/18.png?type=w800'
       , 'https://youtu.be/4HG_CJzyX6A', 'ALL', 1);

INSERT INTO article (id, updated_time, contents, image_url, video_url, open_range, author_id)
VALUES (3, '2019-08-28 23:42:30', 'show friend contents', 'http://mblogthumb3.phinf.naver.net/MjAxNzA2MDhfODYg/MDAxNDk2ODgyNDE3NDYz.yMs2-E3-GlBu9U_4r2GMnBd1IEgVlWG2Qos9pb-2WWIg.M4JN5W9K2kMt9n76gjYQUKPBGt0eHMXE0UrvWFvr6Vgg.PNG.smartbaedal/18.png?type=w800'
       , 'https://youtu.be/5HG_CJzyX6A', 'ONLY_FRIEND', 1);

INSERT INTO article (id, updated_time, contents, image_url, video_url, open_range, author_id)
VALUES (4, '2019-08-28 23:43:30', 'show friend contents', 'http://mblogthumb3.phinf.naver.net/MjAxNzA2MDhfODYg/MDAxNDk2ODgyNDE3NDYz.yMs2-E3-GlBu9U_4r2GMnBd1IEgVlWG2Qos9pb-2WWIg.M4JN5W9K2kMt9n76gjYQUKPBGt0eHMXE0UrvWFvr6Vgg.PNG.smartbaedal/18.png?type=w800'
       , 'https://youtu.be/5HG_CJzyX6A', 'ONLY_FRIEND', 2);

INSERT INTO article (id, updated_time, contents, image_url, video_url, open_range, author_id)
VALUES (5, '2019-08-28 23:44:30', 'show none contents', 'http://mblogthumb3.phinf.naver.net/MjAxNzA2MDhfODYg/MDAxNDk2ODgyNDE3NDYz.yMs2-E3-GlBu9U_4r2GMnBd1IEgVlWG2Qos9pb-2WWIg.M4JN5W9K2kMt9n76gjYQUKPBGt0eHMXE0UrvWFvr6Vgg.PNG.smartbaedal/18.png?type=w800'
       , 'https://youtu.be/5HG_CJzyX6A', 'NONE', 2);

INSERT INTO comment (id, created_time, contents, author_id, article_id)
VALUES (1, '2019-09-01 00:00:00', '첫번째 댓글', 1, 1);

INSERT INTO comment (id, created_time, contents, author_id, article_id, parent_id)
VALUES (2, '2019-09-02 00:00:00', '두번째 댓글', 1, 1, 1);

INSERT INTO comment (id, created_time, contents, author_id, article_id, parent_id)
VALUES (3, '2019-09-01 01:00:00', '세번째 댓글', 1, 1, 1);

INSERT INTO comment (id, created_time, contents, author_id, article_id, parent_id)
VALUES (4, '2019-09-01 00:05:00', '네번째 댓글', 1, 1, 1);

INSERT INTO comment (id, contents, author_id, article_id)
VALUES (5, '다섯번째 댓글', 1, 1);

INSERT INTO comment (id, contents, author_id, article_id)
VALUES (6, '여섯번째 댓글', 1, 1);