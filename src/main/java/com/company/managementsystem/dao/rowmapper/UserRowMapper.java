package com.company.managementsystem.dao.rowmapper;

import com.company.managementsystem.entity.Role;
import com.company.managementsystem.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEnabled(rs.getBoolean("enabled"));
        user.setEmail(rs.getString("email"));

        List<Role> roles = new ArrayList<>();
        while (!rs.isAfterLast() && rs.getLong("id") == user.getId()) {
            Long roleId = rs.getLong("id");
            String roleName = rs.getString("name");
            roles.add(new Role(roleId, roleName));
            rs.next();
        }
        user.setRoles(roles);
        return user;
    }
}