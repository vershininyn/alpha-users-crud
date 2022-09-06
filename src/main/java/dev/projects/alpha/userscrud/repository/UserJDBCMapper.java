package dev.projects.alpha.userscrud.repository;

import dev.projects.alpha.userscrud.entity.UserEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserJDBCMapper implements RowMapper<UserEntity> {

    @Override
    public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UserEntity.builder()
                .id(rs.getLong("ID"))
                .login(rs.getString("LOGIN"))
                .password(rs.getString("PASSWORD"))
                .firstname(rs.getString("FIRSTNAME"))
                .secondname(rs.getString("SECONDNAME"))
                .thirdname(rs.getString("THIRDNAME"))
                .isBanned(rs.getBoolean("IS_BANNED"))
                .build();
    }
}
