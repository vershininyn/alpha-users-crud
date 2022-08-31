package dev.projects.alpha.userscrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findByIsBanned(boolean banned);
}