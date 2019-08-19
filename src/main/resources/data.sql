INSERT INTO user (email, name, password)
VALUES ('a@a.a', 'dpudpu', '1234');

INSERT INTO post (contents, user_id)
VALUES ('contents', 1);
INSERT INTO post (contents, user_id)
VALUES ('contents', 1);

INSERT INTO comment(contents, user_id, post_id)
VALUES ('comment1', 1, 1);
INSERT INTO comment(contents, user_id, post_id)
VALUES ('comment2', 1, 1);
INSERT INTO comment(contents, user_id, post_id)
VALUES ('comment3', 1, 1);
INSERT INTO comment(contents, user_id, post_id, parent_id)
VALUES ('comment4', 1, 1, 1);
INSERT INTO comment(contents, user_id, post_id, parent_id)
VALUES ('comment5', 1, 1, 1);