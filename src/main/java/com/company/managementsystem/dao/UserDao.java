package com.company.managementsystem.dao;

import com.company.managementsystem.entity.User;

public interface UserDao {

    User findUserByUsername(String username);
    void saveUser(User user);

}
