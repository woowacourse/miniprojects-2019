INSERT INTO user (id, email, name, password)
VALUES (1, 'ddu0422@naver.com', 'mir', 'asdf1234!A');

INSERT INTO user (id, email, name, password)
VALUES (2, 'eara12sa@naver.com', 'abc', 'asdf1234!A');

INSERT INTO article (id, contents, image_url, video_url, author_id)
VALUES (1, 'SunBook Contents', 'https://file.namu.moe/file/105db7e730e1402c09dcf2b281232df017f0966ba63375176cb0886869b81bf206145de5a7a149a987d6aae2d5230afaae4ca2bf0b418241957942ad4f4a08c8'
, 'https://youtu.be/mw5VIEIvuMI', 1);

INSERT INTO article (id, contents, image_url, video_url, author_id)
VALUES (2, 'Update Contents', 'http://mblogthumb2.phinf.naver.net/MjAxNzA2MDhfODYg/MDAxNDk2ODgyNDE3NDYz.yMs2-E3-GlBu9U_4r2GMnBd1IEgVlWG2Qos9pb-2WWIg.M4JN5W9K2kMt9n76gjYQUKPBGt0eHMXE0UrvWFvr6Vgg.PNG.smartbaedal/18.png?type=w800'
       , 'https://youtu.be/4HG_CJzyX6A', 1);

INSERT INTO article (id, contents, image_url, video_url, author_id)
VALUES (3, 'Deleted Contents', 'http://mblogthumb3.phinf.naver.net/MjAxNzA2MDhfODYg/MDAxNDk2ODgyNDE3NDYz.yMs2-E3-GlBu9U_4r2GMnBd1IEgVlWG2Qos9pb-2WWIg.M4JN5W9K2kMt9n76gjYQUKPBGt0eHMXE0UrvWFvr6Vgg.PNG.smartbaedal/18.png?type=w800'
       , 'https://youtu.be/5HG_CJzyX6A', 1);

INSERT INTO comment (id, contents, author_id, article_id)
VALUES (1, '첫번째 댓글', 1, 1);

INSERT INTO comment (id, contents, author_id, article_id)
VALUES (2, '두번째 댓글', 1, 1);
