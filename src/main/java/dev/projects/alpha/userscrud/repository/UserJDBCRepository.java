package dev.projects.alpha.userscrud.repository;

import dev.projects.alpha.userscrud.entity.UserEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("user-jdbc-entity-repository")
//@Transactional(
//        readOnly = true
//)
public class UserJDBCRepository implements UserEntityRepository {
    private JdbcTemplate jdbcTemplate;

    @Value("${spring.jdbcTemplates.create-user-sql}")
    private String INSERT_STATEMENT;

//    @Value("${spring.jdbcTemplate.changed-update-user-sql}")
//    private String CHANGED_UPDATE_STATEMENT;

//    @Value("${spring.jdbcTemplate.ban-user-sql}")
//    private String BAN_USER_STATEMENT;

    @Value("${spring.jdbcTemplates.select-all-users-sql}")
    private String SELECT_ALL_USERS;

    @Value("${spring.jdbcTemplates.select-user-by-id-sql}")
    private String SELECT_USER_BY_ID;

    @Value("${spring.jdbcTemplates.select-all-unbanned-users-sql}")
    private String SELECT_ALL_UNBANNED_USERS;

    @Value("${spring.jdbcTemplates.delete-user-by-id-sql}")
    private String DELETE_USER_BY_ID;

    public UserJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*public UserDTO createUser(UserRequestDTO userRequest) {

    }

    public UserDTO changeUser(UserDTO user) {
        UserEntity userEntity = jdbcTemplate.queryForObject(CHANGED_UPDATE_STATEMENT,
                new UserJDBCMapper(),
                new Object[]{user.getLogin(),
                        user.getPassword(),
                        user.getFirstname(),
                        user.getSecondname(),
                        user.getThirdname(),
                        user.isBanned(),
                        user.getId()});

        return UserMapperUtil.mapEntityToDto(userEntity);
    }

    @Override
    public UserDTO banUser(Map<String, String> dto) {
        UserEntity userEntity = jdbcTemplate.queryForObject(BAN_USER_STATEMENT, new UserJDBCMapper(), new Object[]{Long.valueOf(dto.get("id"))});
        userEntity.setIsBanned(true);

        return UserMapperUtil.mapEntityToDto(userEntity);
    }

    @Override
    public List<UserDTO> getAllUsers() {


        return UserMapperUtil.mapEntityListToDtoList(userList);
    }

    @Override
    public List<UserDTO> getAllUnbannedUsers() {
        List<UserEntity> userList = jdbcTemplate.queryForList(SELECT_ALL_UNBANNED_USERS, UserEntity.class);

        return UserMapperUtil.mapEntityListToDtoList(userList);
    }*/

//    @Transactional
    @Override
    public <S extends UserEntity> S save(S entity) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_STATEMENT, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, entity.getLogin());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getFirstname());
            ps.setString(4, entity.getSecondname());
            ps.setString(5, entity.getThirdname());
            ps.setString(6, entity.getIsBanned().toString());

            return ps;
        }, keyHolder);

        entity.setId((long)(keyHolder.getKey().intValue()));

        return entity;
    }

//    @Transactional
    @Override
    public <S extends UserEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

//    @Transactional
    @Override
    public Optional<UserEntity> findById(Long aLong) {
        final UserJDBCMapper mapper = new UserJDBCMapper();

        ResultSetExtractor<Optional<UserEntity>> EXTRACTOR =
                (resultSet) -> resultSet.next() ? Optional.of(mapper.mapRow(resultSet, 1)) : Optional.empty();

        return jdbcTemplate.query(SELECT_USER_BY_ID, EXTRACTOR, new Object[]{aLong});
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Transactional
    @Override
    public Iterable<UserEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL_USERS, new UserJDBCMapper());
    }

    @Override
    public Iterable<UserEntity> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

//    @Transactional
    @Override
    public void deleteById(Long aLong) {
        jdbcTemplate.update(DELETE_USER_BY_ID, new Object[]{aLong});
    }

//    @Transactional
    @Override
    public void delete(UserEntity entity) {
        deleteById(entity.getId());
    }

//    @Transactional
    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        Iterator<? extends Long> iterator = longs.iterator();

        while(iterator.hasNext()) {
            deleteById(iterator.next());
        }
    }

    @Override
    public void deleteAll(Iterable<? extends UserEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

//    @Transactional
    @Override
    public List<UserEntity> findByIsBanned(boolean banned) {
        return jdbcTemplate.queryForList(SELECT_ALL_UNBANNED_USERS, UserEntity.class);
    }
}
