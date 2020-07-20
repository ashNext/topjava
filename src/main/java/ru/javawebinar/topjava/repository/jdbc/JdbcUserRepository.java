package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(@NotNull User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        List<Role> userRoles = new ArrayList<>(user.getRoles());

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else {
            jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.id());
            if (namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
                return null;
            }
        }

        if (jdbcTemplate.batchUpdate(
                "INSERT INTO user_roles (user_id, role)  VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setString(2, userRoles.get(i).toString());
                        preparedStatement.setInt(1, user.id());
                    }

                    @Override
                    public int getBatchSize() {
                        return userRoles.size();
                    }
                }).length == 0) {
            return null;
        }
        return user;
    }

    @Override
    public boolean delete(@PositiveOrZero int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(@PositiveOrZero int id) {
        List<User> users = jdbcTemplate.query(
                "SELECT users.*, string_agg(user_roles.role, ', ') as roles " +
                        "FROM users INNER JOIN user_roles ON users.id = user_roles.user_id " +
                        "WHERE id=?" +
                        "GROUP BY id " +
                        "ORDER BY name, email",
                ROW_MAPPER,
                id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(@Email String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query(
                "SELECT users.*, string_agg(user_roles.role, ', ') as roles " +
                        "FROM users INNER JOIN user_roles ON users.id = user_roles.user_id " +
                        "WHERE email=?" +
                        "GROUP BY id " +
                        "ORDER BY name, email",
                ROW_MAPPER,
                email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(
                "SELECT users.*, string_agg(user_roles.role, ', ') as roles " +
                        "FROM users INNER JOIN user_roles ON users.id = user_roles.user_id " +
                        "GROUP BY id " +
                        "ORDER BY name, email",
                ROW_MAPPER);
    }
}
