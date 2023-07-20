package com.company.managementsystem.dao;

import com.company.managementsystem.entity.Role;

public interface RoleDao {

    Role findRoleByName(String name);

}
