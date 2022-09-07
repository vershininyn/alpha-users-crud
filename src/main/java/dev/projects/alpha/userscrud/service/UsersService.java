package dev.projects.alpha.userscrud.service;

import dev.projects.alpha.userscrud.domain.UserDTO;
import dev.projects.alpha.userscrud.domain.UserRequestDTO;
import dev.projects.alpha.userscrud.entity.UserEntity;
import dev.projects.alpha.userscrud.repository.UserEntityRepository;
import dev.projects.alpha.userscrud.repository.UserRepositoryManager;
import dev.projects.alpha.userscrud.utils.UserMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@ComponentScan("dev.projects.alpha.userscrud.repository")
@PropertySource(value={"classpath:application.yml"})
public class UsersService {
    @Autowired
    private UserRepositoryManager repositoryManager;

    private UserEntityRepository userRepository;

    @Value("${spring.jdbcTemplates.selected-repo}")
    private String SELECTED_REPO;

    public UsersService (UserRepositoryManager repositoryManager) {

    }

    @PostConstruct
    public void initializeService() {
        System.out.println("++++++ USING REPO: "+SELECTED_REPO+" ++++++");

        userRepository = repositoryManager.getRepositoryByEnvVariable(SELECTED_REPO);
    }

    public UserDTO createUser(UserRequestDTO userRequest) {
        UserEntity entity = UserMapperUtil.mapDtoToEntity(userRequest),
                savedEntity = userRepository.save(entity);

        return UserMapperUtil.mapRequestDtoToUserDto(savedEntity.getId(), userRequest);
    }

    public UserDTO changeUser(UserDTO user) {
        UserEntity entity = userRepository.findById(user.getId()).get();

        entity.setLogin(user.getLogin());
        entity.setPassword(user.getPassword());
        entity.setFirstname(user.getFirstname());
        entity.setSecondname(user.getSecondname());
        entity.setThirdname(user.getThirdname());
        entity.setIsBanned(user.isBanned());

        UserEntity savedEntity = userRepository.save(entity);

        return UserMapperUtil.mapEntityToDto(savedEntity);
    }

    public UserDTO banUser(Map<String, String> dto) {
        Long id = Long.valueOf(dto.get("id"));

        UserEntity entity = userRepository.findById(id).get();
        entity.setIsBanned(true);

        UserEntity savedEntity = userRepository.save(entity);

        return UserMapperUtil.mapEntityToDto(savedEntity);
    }

    public List<UserDTO> getAllUsers() {
        List<UserEntity> list = new ArrayList();

        userRepository.findAll().forEach(list::add);

        return UserMapperUtil.mapEntityListToDtoList(list);
    }

    public List<UserDTO> getAllUnbannedUsers() {
        List<UserEntity> list = userRepository.findByIsBanned(false);

        return UserMapperUtil.mapEntityListToDtoList(list);
    }
}
