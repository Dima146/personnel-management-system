package com.company.managementsystem.service;

import com.company.managementsystem.dao.RoleDao;
import com.company.managementsystem.dao.UserDao;
import com.company.managementsystem.entity.Role;
import com.company.managementsystem.entity.User;

import com.company.managementsystem.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_ROLE = "ROLE_EMPLOYEE";
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder, UserDao userDao, RoleDao roleDao) {

        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userDao.findUserByUsername(username));
    }

    @Override
    public void saveUser(User user) {

        Role defaultRole = roleDao.findRoleByName(DEFAULT_ROLE);
        if (defaultRole == null) {
            throw new EntityNotFoundException("Role was not found by name");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(defaultRole));
        userDao.saveUser(user);

    }
}