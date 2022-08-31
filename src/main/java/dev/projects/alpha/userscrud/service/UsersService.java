package dev.projects.alpha.userscrud.service;

import dev.projects.alpha.userscrud.domain.UserDTO;
import dev.projects.alpha.userscrud.domain.UserRequestDTO;
import dev.projects.alpha.userscrud.repository.UserEntity;
import dev.projects.alpha.userscrud.repository.UserEntityRepository;
import dev.projects.alpha.userscrud.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UsersService {
    private UserEntityRepository userRepository;

    @Autowired
    public UsersService (UserEntityRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserRequestDTO userRequest) {
        UserEntity entity = UserMapper.mapDtoToEntity(userRequest),
                savedEntity = userRepository.save(entity);

        return UserMapper.mapRequestDtoToUserDto(savedEntity.getId(), userRequest);
    }

    public UserDTO changeUser(UserDTO user) {
        //TODO: check other branches

        UserEntity entity = userRepository.findById(user.getId()).get();

        return UserMapper.mapEntityToDto(entity);
    }

    public UserDTO banUser(Map<String, Integer> dto) {
        Integer id = dto.get("id");

        UserEntity entity = userRepository.findById(id).get();
        entity.setIsBanned(true);

        UserEntity savedEntity = userRepository.save(entity);

        return UserMapper.mapEntityToDto(savedEntity);
    }

    public List<UserDTO> getAllUsers() {
        List<UserEntity> list = userRepository.findAll();

        return UserMapper.mapEntityListToDtoList(list);
    }

    public List<UserDTO> getAllUnbannedUsers() {
        List<UserEntity> list = userRepository.findByIsBanned(false);

        return UserMapper.mapEntityListToDtoList(list);
    }
}
