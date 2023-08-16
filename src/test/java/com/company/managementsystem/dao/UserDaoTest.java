package com.company.managementsystem.dao;

import com.company.managementsystem.dao.impl.RoleDaoImpl;
import com.company.managementsystem.dao.impl.UserDaoImpl;
import com.company.managementsystem.dao.rowmapper.RoleRowMapper;
import com.company.managementsystem.dao.rowmapper.UserRowMapper;
import com.company.managementsystem.entity.Role;
import com.company.managementsystem.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {UserDaoImpl.class, UserRowMapper.class, RoleDaoImpl.class, RoleRowMapper.class})
public class UserDaoTest {

    private static final String DEFAULT_ROLE = "ROLE_EMPLOYEE";

    @Autowired
    private UserDaoImpl userDao;

    @Test
    public void UserDao_GetByUsername_ReturnUser() {

        User user = new User("username", "password",
                               true, "First Name",
                              "Last Name", "email@mail.com", List.of(new Role(DEFAULT_ROLE)));

        userDao.saveUser(user);

        User result = userDao.findUserByUsername(user.getUsername());

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(user.getUsername());

    }
}