CREATE TABLE IF NOT EXISTS users(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(25) UNIQUE,
    password VARCHAR(50),
    firstname VARCHAR(25),
    secondname VARCHAR(25),
    thirdname VARCHAR(25),
    is_banned BOOLEAN DEFAULT false
);