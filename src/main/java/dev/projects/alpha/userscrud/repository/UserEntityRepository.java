package dev.projects.alpha.userscrud.repository;

import dev.projects.alpha.userscrud.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByIsBanned(boolean banned);
}