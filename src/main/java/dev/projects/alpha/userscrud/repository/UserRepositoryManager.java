package dev.projects.alpha.userscrud.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserRepositoryManager {
    @Autowired
    @Qualifier("user-jpa-entity-repository")
    private UserEntityRepository jpaRepository;

    @Autowired
    @Qualifier("user-jdbc-entity-repository")
    private UserJDBCRepository jdbcRepository;

    private final Map<String, UserEntityRepository> repositoryMap = new HashMap<>(2);

    public UserRepositoryManager() {

    }

    @PostConstruct
    public void initializeManager() {
        repositoryMap.put("JPA", jpaRepository);
        repositoryMap.put("JDBC", jdbcRepository);
    }

    public UserEntityRepository getRepositoryByEnvVariable(String variable) {
        return repositoryMap.get(variable.toUpperCase());
    }
}
