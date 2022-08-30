package dev.projects.alpha.userscrud.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/*
CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    login VARCHAR(25) UNIQUE,
    password VARCHAR(50),
    name VARCHAR(25),
    surname VARCHAR(25),
    patronymic VARCHAR(25),
    is_banned BOOLEAN DEFAULT false
);
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class UserDTO {
    private UUID id;
    private String login;
    private String password;
    private String firstname;
    private String secondname;
    private String thirdname;
    private boolean isBanned;
}
