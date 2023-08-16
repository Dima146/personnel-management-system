package com.company.managementsystem.dao.impl;

import com.company.managementsystem.dao.RoleDao;
import com.company.managementsystem.entity.Role;
import com.company.managementsystem.exception.DaoException;
import com.company.managementsystem.dao.rowmapper.RoleRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private final RoleRowMapper roleRowMapper;

    private static final String FIND_ROLE_BY_NAME = "SELECT * FROM role WHERE name=?";

    @Autowired
    public RoleDaoImpl(JdbcTemplate jdbcTemplate, RoleRowMapper roleRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.roleRowMapper = roleRowMapper;
    }

    @Override
    public Role findRoleByName(String name) {
        Role role;

        try {
            role = jdbcTemplate.queryForObject(FIND_ROLE_BY_NAME, roleRowMapper, name);
            return role;

        } catch (EmptyResultDataAccessException exception) {
            return null;

        } catch (DataAccessException exception) {
            LOGGER.error("Error while retrieving an role by name", exception);
            throw new DaoException(exception);
        }
    }
}
