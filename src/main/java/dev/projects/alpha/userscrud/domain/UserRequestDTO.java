package dev.projects.alpha.userscrud.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    private String login;
    private String password;
    private String firstname;
    private String secondname;
    private String thirdname;
    private boolean isBanned;
}
