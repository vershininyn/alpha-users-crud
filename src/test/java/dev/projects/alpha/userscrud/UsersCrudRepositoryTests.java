package dev.projects.alpha.userscrud;

import dev.projects.alpha.userscrud.entity.UserEntity;
import dev.projects.alpha.userscrud.repository.UserEntityRepository;
import dev.projects.alpha.userscrud.repository.UserRepositoryManager;
import org.assertj.core.util.Lists;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class UsersCrudRepositoryTests {
    /*private static final PostgreSQLContainer postgresSQLContainer;

    static {
        int containerPort = 5432;
        int localPort = 45362;

        DockerImageName postgres = DockerImageName.parse("postgres:10-alpine");

        postgresSQLContainer = new PostgreSQLContainer<>(postgres)
                .withDatabaseName("test")
                .withUsername("user")
                .withPassword("password")
                .withReuse(true)
                .withExposedPorts(containerPort)
                .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                        new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(localPort), new ExposedPort(containerPort)))
                ));

        postgresSQLContainer.start();
    }

    @SuppressWarnings("rawtypes")
    private static PostgreSQLContainer getPostgresSQLContainer() {
        return postgresSQLContainer;
    }

    @SuppressWarnings("unused")
    @DynamicPropertySource
    public static void registerPgProperties(DynamicPropertyRegistry propertyRegistry) {
//        propertyRegistry.add("test", getPostgresSQLContainer()::getDatabaseName);

////        try {
////            Class.forName("org.postgres.Driver");
////        } catch (ClassNotFoundException e) {
////            throw new RuntimeException(e);
////        }
//
//        propertyRegistry.add("spring.datasource.type", () -> "com.zaxxer.hikari.HikariDataSource");
//        propertyRegistry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
//        propertyRegistry.add("spring.datasource.username", getPostgresSQLContainer()::getUsername);
//        propertyRegistry.add("spring.datasource.password", getPostgresSQLContainer()::getPassword);
        propertyRegistry.add("spring.datasource.url", getPostgresSQLContainer()::getJdbcUrl);

    }*/

    @Autowired
    private UserRepositoryManager userRepositoryManager;

    @ParameterizedTest
    @CsvSource(value = {"JPA", "JDBC"})
    public void checkFindAllUsers(String repositoryType) {
        UserEntityRepository userRepository = userRepositoryManager.getRepositoryByEnvVariable(repositoryType);

        ArrayList<UserEntity> usersEntities = Lists.newArrayList(userRepository.findAll());

        List<String> logins = usersEntities.stream()
                .map(UserEntity::getLogin)
                .collect(Collectors.toList());

        assertEquals(3, logins.size());
        assertEquals(List.of("first_user", "second_user", "third_user"), logins);
        assertFalse(logins.contains("fourth_user"));
    }

    @ParameterizedTest
    @CsvSource(value = {"JPA", "JDBC"})
    public void checkFindByCorrectIdUser(String repositoryType) {
        UserEntityRepository userRepository = userRepositoryManager.getRepositoryByEnvVariable(repositoryType);

        UserEntity entity = userRepository.findById(1L).get();

        assertEquals(1L, entity.getId());
        assertEquals("first_user", entity.getLogin());
        assertEquals("password", entity.getPassword());
        assertEquals("firstname", entity.getFirstname());
        assertEquals("secondname", entity.getSecondname());
        assertEquals("thirdname", entity.getThirdname());
        assertEquals(false, entity.getIsBanned());
    }

    @ParameterizedTest
    @CsvSource(value = {"JPA", "JDBC"})
    public void checkFindByIncorrectIdUser(String repositoryType) {
        UserEntityRepository userRepository = userRepositoryManager.getRepositoryByEnvVariable(repositoryType);

        final Optional<UserEntity> entity = userRepository.findById(-1L);

        assertThrows(IllegalArgumentException.class, () -> {
            entity.orElseThrow(() -> new IllegalArgumentException("The some IAE"));
        });
    }

    @ParameterizedTest
    @CsvSource(value = {"JPA", "JDBC"})
    public void checkSaveUser(String repositoryType) {
        UserEntityRepository userRepository = userRepositoryManager.getRepositoryByEnvVariable(repositoryType);

        UserEntity entity = UserEntity.builder()
                .login(UUID.randomUUID().toString().substring(0, 15))
                .password("password")
                .firstname("firstname")
                .secondname("secondname")
                .thirdname("thirdname")
                .isBanned(false)
                .build();

        UserEntity result = userRepository.save(entity);

        List<UserEntity> users = Lists.newArrayList(userRepository.findAll());

        assertTrue(users.size() > 3);
    }
}
