CREATE TABLE IF NOT EXISTS public.users(
    id INT NOT NULL PRIMARY KEY DEFAULT nextval('users_sequence'),
    login VARCHAR(25) UNIQUE,
    password VARCHAR(50),
    firstname VARCHAR(25),
    secondname VARCHAR(25),
    thirdname VARCHAR(25),
    is_banned BOOLEAN DEFAULT false
);