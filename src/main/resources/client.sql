# You should create this database & table first on your client's dbms (MySQL or SQLite)

CREATE DATABASE client_online_chatter;
USE client_online_chatter;
CREATE TABLE client_message
(
    msg_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    from_id INTEGER,
    to_id INTEGER,
    message VARCHAR(1024),
    sent_time DATETIME,
    user_id INTEGER
);
