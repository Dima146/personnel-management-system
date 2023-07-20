package com.company.managementsystem.service;

import com.company.managementsystem.entity.Role;
import com.company.managementsystem.entity.User;
import com.company.managementsystem.dao.RoleDaoImpl;
import com.company.managementsystem.dao.UserDaoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final String DEFAULT_ROLE = "ROLE_EMPLOYEE";

    @Mock
    private UserDaoImpl userDao;
    @Mock
    private RoleDaoImpl roleDao;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void UserService_FindByUsername_ReturnUser() {

        User user = new User("username", "password", true,
                "First Name", "Last Name", "email@mail.com",
                List.of(new Role(DEFAULT_ROLE)));

        when(userDao.findUserByUsername("email@mail.com")).thenReturn(user);

        Optional<User> result = userService.findByUsername("email@mail.com");

        assertThat(result).isNotEmpty();
        assertThat(result.get().getUsername()).isEqualTo(user.getUsername());

    }

    @Test
    public void UserService_SaveUser_ReturnUser() {

        Role role = new Role(DEFAULT_ROLE);

        User user = new User("username", "password", true,
                "First Name", "Last Name", "email@mail.com",
                List.of(new Role(DEFAULT_ROLE)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        when(roleDao.findRoleByName(DEFAULT_ROLE)).thenReturn(role);
        when(userDao.findUserByUsername("username")).thenReturn(user);

        userService.saveUser(user);

        Optional<User> result = userService.findByUsername("username");

        assertThat(result).isNotEmpty();
        assertThat(result.get().getUsername()).isEqualTo("username");

    }
}