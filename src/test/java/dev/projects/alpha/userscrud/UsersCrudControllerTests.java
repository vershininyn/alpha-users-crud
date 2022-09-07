package dev.projects.alpha.userscrud;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.projects.alpha.userscrud.controller.UsersController;
import dev.projects.alpha.userscrud.domain.UserDTO;
import dev.projects.alpha.userscrud.domain.UserRequestDTO;
import dev.projects.alpha.userscrud.domain.UsersListDTO;
import dev.projects.alpha.userscrud.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class UsersCrudControllerTests {
    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersController usersController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createUserByControllerTest() throws Exception {
        File jsonNewUserDTOFile = ResourceUtils.getFile("classpath:json/in/new-user.json");

        UserRequestDTO newUserRequestDTO = objectMapper.readValue(jsonNewUserDTOFile, UserRequestDTO.class);

        ResponseEntity<Map<String, String>> responseEntity = usersController.createUser(newUserRequestDTO);

        assertEquals(responseEntity.getStatusCodeValue(), 201);
        assertEquals(responseEntity.getHeaders().getLocation().getPath(), "/4");
        assertEquals(responseEntity.getBody().size(), 1);
        assertEquals(responseEntity.getBody().get("id"), "4");
    }

    @Test
    public void changedUserByControllerTest() throws Exception {
        File jsonChangedUserDTOFile = ResourceUtils.getFile("classpath:json/in/changed-user.json");

        UserDTO changedUserDTO = objectMapper.readValue(jsonChangedUserDTOFile, UserDTO.class);

        ResponseEntity<UserDTO> responseEntity = usersController.changeUser(changedUserDTO);

        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertEquals(responseEntity.getBody().getId(), 1);
        assertEquals(responseEntity.getBody().getFirstname(), "firstname");
        assertEquals(responseEntity.getBody().getSecondname(), "secondname");
        assertEquals(responseEntity.getBody().getThirdname(), "thirdname");
    }

    @Test
    public void banUserByID() throws Exception {
        File jsonForBanUserDTOFile = ResourceUtils.getFile("classpath:json/in/user-for-ban.json");

        Map<String, String> changedUserDTO = objectMapper.readValue(jsonForBanUserDTOFile, Map.class);

        ResponseEntity<UserDTO> responseEntity = usersController.banUser(changedUserDTO);

        assertEquals(responseEntity.getStatusCodeValue(), 200);
        assertEquals(responseEntity.getBody().getId(), 1);
        assertEquals(responseEntity.getBody().isBanned(), true);
    }

    @Test
    public void getAllUsers() throws Exception {
        ResponseEntity<UsersListDTO> responseEntity = usersController.getAllUsers();

        assertEquals(responseEntity.getStatusCodeValue(), 200);

        UsersListDTO list = responseEntity.getBody();
        assertEquals(3, list.getUsers().size());

        List<Long> ids = list.getUsers()
                .stream()
                .map(UserDTO::getId)
                .collect(Collectors.toList());

        assertEquals(List.of(1L, 2L, 3L), ids);
        assertFalse(ids.contains(4L));

        List<String> logins = list.getUsers()
                .stream()
                .map(UserDTO::getLogin)
                .collect(Collectors.toList());

        assertEquals(List.of("first_user", "second_user", "third_user"), logins);
        assertFalse(logins.contains("fourth_user"));
    }

    @Test
    public void getAllUnbannedUsers() throws Exception {
        ResponseEntity<UsersListDTO> responseEntity = usersController.getAllUnbannedUsers();

        assertEquals(responseEntity.getStatusCodeValue(), 200);

        UsersListDTO list = responseEntity.getBody();
        assertEquals(3, list.getUsers().size());

        boolean unbanned = list.getUsers()
                .stream()
                .allMatch(user -> user.isBanned() == false);

        assertEquals(true, unbanned);
    }
}
