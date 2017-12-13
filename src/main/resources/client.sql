CREATE DATABASE client_online_chatter;
USE client_online_chatter
CREATE TABLE client_message
(
    msg_id INTEGER PRIMARY KEY,
    user_id INTEGER,
    from_id INTEGER,
    to_id INTEGER,
    message VARCHAR(1024),
    sent_time DATETIME
);
