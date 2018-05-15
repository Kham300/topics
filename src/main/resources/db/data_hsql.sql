DELETE FROM USERS;
DELETE FROM TOPICS;
ALTER SEQUENCE global_seq RESTART WITH 100000;


INSERT INTO USERS (NAME, EMAIL, ABOUT, PASSWORD, REGISTERED, ENABLED) VALUES
                  ('User1', 'email1@mail.ru', 'about1', 'pass', '2018-05-13 10:00:00', false),
                  ('User2', 'email2@mail.ru', 'about2', 'pass', NOW(), false);

insert INTO TOPICS (DATE_TIME, DESCRIPTION, TEXT, USER_ID) VALUES
                   ('2018-05-13 11:00:00', 'description 1', 'text1', 100000),
                   ('2018-05-12 11:00:00', 'description 2', 'text1', 100001),
                   ('2018-05-11 11:00:00', 'description 3', 'text1', 100001),
                   (now(), 'description 2', 'text2', 100001);

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001);

--INSERT INTO tutorials_tbl VALUES (100,'Learn PHP', 'John Poul', NOW());