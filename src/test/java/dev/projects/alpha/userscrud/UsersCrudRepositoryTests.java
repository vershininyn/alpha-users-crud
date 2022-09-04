package dev.projects.alpha.userscrud;

import dev.projects.alpha.userscrud.entity.UserEntity;
import dev.projects.alpha.userscrud.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")
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
    private UserEntityRepository userRepository;

    @Test
    public void checkAllUsers() {
        List<String> logins = userRepository.findAll()
                .stream()
                .map(UserEntity::getLogin)
                .collect(Collectors.toList());

        assertEquals(3, logins.size());
        assertEquals(List.of("first_user", "second_user", "third_user"), logins);
        assertFalse(logins.contains("fourth_user"));
    }
}
