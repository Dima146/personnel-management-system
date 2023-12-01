package com.company.managementsystem.dao.impl;

import com.company.managementsystem.dao.UserDao;
import com.company.managementsystem.entity.Role;
import com.company.managementsystem.entity.User;
import com.company.managementsystem.exception.DaoException;
import com.company.managementsystem.dao.rowmapper.UserRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
    private final UserRowMapper userRowMapper;
    private final JdbcTemplate jdbcTemplate;

    private static final String FIND_USER_BY_USERNAME =
            "SELECT * FROM user INNER JOIN users_roles ON user.id = users_roles.user_id " +
             "INNER JOIN role ON users_roles.role_id = role.id WHERE username = ?";

    private static final String SAVE_USER =
            "INSERT INTO user (username, password, enabled, first_name, last_name, email) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SET_ASSOCIATION_USERS_ROLES =
            "INSERT INTO users_roles (user_id, role_id) " +
                    "SELECT " +
                    "(SELECT id FROM user WHERE username = ?), " +
                    "(SELECT id FROM role WHERE role.name = ?)";


    @Autowired
    public UserDaoImpl(UserRowMapper userRowMapper, JdbcTemplate jdbcTemplate) {
        this.userRowMapper = userRowMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findUserByUsername(String username) {
        User user;
        try {
            user = jdbcTemplate.queryForObject(FIND_USER_BY_USERNAME, userRowMapper, username);
            return user;
        } catch (EmptyResultDataAccessException exception) {
            return null;
        } catch (DataAccessException exception) {
            LOGGER.error("Error while retrieving an user by username", exception);
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void saveUser(User user) {
        try {
            jdbcTemplate.update(SAVE_USER, user.getUsername(), user.getPassword(), user.isEnabled(),
                                            user.getFirstName(), user.getLastName(), user.getEmail());
            for (Role role : user.getRoles()) {
                jdbcTemplate.update(SET_ASSOCIATION_USERS_ROLES, user.getUsername(), role.getName());
            }
        } catch (DataAccessException exception) {
            LOGGER.error("Error while saving an user", exception);
            throw new DaoException(exception);
        }
    }
}