package com.company.managementsystem.service;

import com.company.managementsystem.entity.User;
import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);
    void saveUser(User user);

}
