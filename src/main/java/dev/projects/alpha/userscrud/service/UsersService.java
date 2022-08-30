package dev.projects.alpha.userscrud.service;

import dev.projects.alpha.userscrud.domain.UserDTO;
import dev.projects.alpha.userscrud.domain.UserRequestDTO;
import dev.projects.alpha.userscrud.domain.UserUUIDDTO;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

@Service
public class UsersService {

    //Создание пользователя, изменение пользователя, бан пользователя по ID, получение всех пользователей, получение незабаненных пользователей.

    public UserDTO createUser(UserRequestDTO userRequest) {
        return createUser(UUID.randomUUID(), userRequest);
    }

    public UserDTO createUser(UUID uuid, UserRequestDTO userRequest) {
        return UserDTO.builder()
                .id(uuid)
                .login(userRequest.getLogin())
                .password(userRequest.getPassword())
                .firstname(userRequest.getFirstname())
                .secondname(userRequest.getSecondname())
                .thirdname(userRequest.getThirdname())
                .isBanned(false)
                .build();
    }

    public UserDTO changeUser(UserDTO changedUser) {
        return changedUser;
    }

    public UserDTO banUser(UserUUIDDTO uuidUserDTO) {
        return UserDTO.builder()
                .id(uuidUserDTO.getUuid())
                .login("login")
                .password("password")
                .firstname("firstname")
                .secondname("secondname")
                .thirdname("thirdname")
                .isBanned(true)
                .build();
    }

    public List<UserDTO> getAllUsers() {
        UserDTO user = UserDTO.builder()
                .id(UUID.randomUUID())
                .login("login")
                .password("password")
                .firstname("firstname")
                .secondname("secondname")
                .thirdname("thirdname")
                .isBanned(false)
                .build();

        return Stream.of(user, user, user).collect(Collectors.toList());
    }

    public List<UserDTO> getAllUnbannedUsers() {
        UserDTO user = UserDTO.builder()
                .id(UUID.randomUUID())
                .login("login")
                .password("password")
                .firstname("firstname")
                .secondname("secondname")
                .thirdname("thirdname")
                .isBanned(false)
                .build();

        return Stream.of(user, user, user).collect(Collectors.toList());
    }
}
