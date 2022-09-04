package dev.projects.alpha.userscrud.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class UserDTO {
    private Long id;
    private String login;
    private String password;
    private String firstname;
    private String secondname;
    private String thirdname;
    private boolean isBanned;
}
