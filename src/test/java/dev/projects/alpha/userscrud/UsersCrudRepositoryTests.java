package dev.projects.alpha.userscrud;

import dev.projects.alpha.userscrud.repository.UserEntity;
import dev.projects.alpha.userscrud.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest()
@ActiveProfiles("test")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsersCrudRepositoryTests {
    @Autowired
    private UserEntityRepository userRepository;

    @Test
    public void checkAllEntity() {
        List<String> entityList = userRepository.findAll()
                .stream()
                .map(UserEntity::getFirstname)
                .collect(Collectors.toList());

        assertEquals(List.of("first_name", "second_name", "third_name"), entityList);
    }
}
