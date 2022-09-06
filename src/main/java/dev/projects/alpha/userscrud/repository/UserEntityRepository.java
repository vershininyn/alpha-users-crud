package dev.projects.alpha.userscrud.repository;

import dev.projects.alpha.userscrud.entity.UserEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("user-jpa-entity-repository")
public interface UserEntityRepository extends CrudRepository<UserEntity, Long> {
    List<UserEntity> findByIsBanned(boolean banned);

    /*UserDTO createUser(UserRequestDTO userRequest);

    UserDTO changeUser(UserDTO user);

    UserDTO banUser(Map<String, String> dto);

    List<UserDTO> getAllUsers();

    List<UserDTO> getAllUnbannedUsers();*/
}
