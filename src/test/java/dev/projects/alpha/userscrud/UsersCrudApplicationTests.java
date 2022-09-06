package dev.projects.alpha.userscrud;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.projects.alpha.userscrud.domain.UserDTO;
import dev.projects.alpha.userscrud.domain.UserRequestDTO;
import dev.projects.alpha.userscrud.repository.UserEntityRepository;
import dev.projects.alpha.userscrud.service.UsersService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class UsersCrudApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UsersService usersService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createUserTest() throws Exception {
        File jsonNewUserDTOFile = ResourceUtils.getFile("classpath:json/in/new-user.json");

        UserRequestDTO newUserRequestDTO = objectMapper.readValue(jsonNewUserDTOFile, UserRequestDTO.class);

        when(usersService.createUser(newUserRequestDTO))
                .thenAnswer((Answer<UserDTO>) invocationOnMock -> invocationOnMock.getArgument(0, UserDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserRequestDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void changeUserTest() throws Exception {
        File jsonChangedUserDTOFile = ResourceUtils.getFile("classpath:json/in/changed-user.json");

        UserDTO changedUserDTO = objectMapper.readValue(jsonChangedUserDTOFile, UserDTO.class);

        when(usersService.changeUser(changedUserDTO))
                .thenAnswer((Answer<UserDTO>) invocationOnMock -> invocationOnMock.getArgument(0, UserDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/changeUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changedUserDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", is("firstname")))
                .andExpect(jsonPath("$.secondname", is("secondname")))
                .andExpect(jsonPath("$.thirdname", is("thirdname")));
    }

    @Test
    public void banUserByID() throws Exception {
        File jsonForBanUserDTOFile = ResourceUtils.getFile("classpath:json/in/user-for-ban.json");

        Map<String, String> dto = objectMapper.readValue(jsonForBanUserDTOFile, Map.class);

        when(usersService.banUser(dto))
                .thenAnswer((Answer<UserDTO>) invocationOnMock -> invocationOnMock.getArgument(0, UserDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/banUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.banned", is(true)));
    }

    @Test
    public void getAllUsers() throws Exception {
        when(usersService.getAllUsers())
                .thenAnswer((Answer<List<UserDTO>>) invocationOnMock -> invocationOnMock.getArgument(0, List.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/getAllUsers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users").isArray())
                .andExpect(jsonPath("$.users", hasSize(3)));
    }

    @Test
    public void getAllUnbannedUsers() throws Exception {
        when(usersService.getAllUnbannedUsers())
                .thenAnswer((Answer<List<UserDTO>>) invocationOnMock -> invocationOnMock.getArgument(0, List.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/getAllUnbannedUsers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users").isArray())
                .andExpect(jsonPath("$.users", hasSize(3)))
                .andExpect(jsonPath("$.users[0].banned", is(false)))
                .andExpect(jsonPath("$.users[1].banned", is(false)))
                .andExpect(jsonPath("$.users[2].banned", is(false)));
    }
}
