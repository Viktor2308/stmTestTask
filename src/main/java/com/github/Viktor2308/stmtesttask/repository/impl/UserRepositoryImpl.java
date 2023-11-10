package com.github.Viktor2308.stmtesttask.repository.impl;

import com.github.Viktor2308.stmtesttask.entity.Role;
import com.github.Viktor2308.stmtesttask.entity.Ticket;
import com.github.Viktor2308.stmtesttask.entity.User;
import com.github.Viktor2308.stmtesttask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final String SAVE_USER_SQL = "INSERT INTO entity.users (login, password, fullname, role) VALUES (?,?,?,?)";
    private final String UPDATE_USER_SQL = "UPDATE entity.users SET login=?, password=?, fullname=?, role=? WHERE id=?";
    private final String FIND_USER_BY_LOGIN_SQL = "SElECT * FROM entity.users WHERE login=?";
    private final String FIND_ALL_USER_SQL = "SELECT * FROM entity.users";

    @Override
    public int save(User user) {
        return jdbcTemplate.update(SAVE_USER_SQL,
                new Object[]{user.getLogin(), user.getPassword(), user.getFullName(), user.getRole().toString()});
    }

    @Override
    public int update(User user) {
        return jdbcTemplate.update(UPDATE_USER_SQL,
                new Object[]{user.getLogin(), user.getPassword(), user.getFullName(), user.getRole().toString(), user.getId()});
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try {
            User user = jdbcTemplate.queryForObject(FIND_USER_BY_LOGIN_SQL,
                    BeanPropertyRowMapper.newInstance(User.class), login);
            return Optional.of(user);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL_USER_SQL, new UserRowMapper() {
        });
    }

    private static class UserRowMapper implements RowMapper<User>{
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(UUID.fromString(rs.getString("id")));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setFullName(rs.getString("fullName"));
            user.setRole(Role.valueOf(rs.getString("role")));
            return user;
        }
    }
}
