CREATE DATABASE IF NOT EXISTS videoStreaming;


USE videoStreaming;

CREATE TABLE video (
    owner varchar(20),
    name varchar(20),
    path varchar(250) PRIMARY KEY
);


CREATE USER 'managementApp'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON videoStreaming.* TO 'managementApp'@'%';

CREATE USER 'streamingApp'@'%' IDENTIFIED BY 'password';
GRANT SELECT ON VideoStreaming.* TO 'streamingApp'@'%';

FLUSH PRIVILEGES;