DROP TABLE IF EXISTS USER_TABLE;

CREATE TABLE USER_TABLE (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    email VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL,
    firstname VARCHAR(250) NOT NULL,
    lastname VARCHAR(250) NOT NULL
);