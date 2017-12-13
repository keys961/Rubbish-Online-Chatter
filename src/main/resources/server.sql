CREATE DATABASE server_online_chatter;
USE server_online_chatter
CREATE TABLE server_user
(
  uid INTEGER,
  username VARCHAR(40),
  password VARCHAR(20),
  PRIMARY KEY(uid, username)
);
