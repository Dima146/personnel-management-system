package com.company.managementsystem.dao.impl;

import com.company.managementsystem.dao.EmployeeDao;
import com.company.managementsystem.entity.Employee;
import com.company.managementsystem.exception.DaoException;
import com.company.managementsystem.dao.rowmapper.EmployeeRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    private static final String FIND_ALL_EMPLOYEES = "SELECT * FROM employee ORDER BY last_name ASC";

    private static final String FIND_EMPLOYEES_BY_FIRST_OR_LAST_NAME =
            "SELECT * FROM employee where first_name LIKE lower(concat(?, '%')) or last_name LIKE lower(concat(?, '%'))";
    private static final String FIND_EMPLOYEE_BY_EMAIL = "SELECT * FROM employee where email=?";

    private static final String FIND_EMPLOYEE_BY_ID = "SELECT * FROM employee where id=?";

    private static final String SAVE_EMPLOYEE = "INSERT INTO employee (first_name, last_name, email) VALUES(?, ?, ?)";

    private static final String UPDATE_EMPLOYEE = "UPDATE employee SET first_name = ?, last_name = ?, email = ? " +
                                                        "WHERE id=?";

    private static final String DELETE_EMPLOYEE = "DELETE FROM employee WHERE id=?";


    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private final EmployeeRowMapper employeeRowMapper;

    @Autowired
    public EmployeeDaoImpl(JdbcTemplate jdbcTemplate, EmployeeRowMapper employeeRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.employeeRowMapper = employeeRowMapper;
    }

    @Override
    public List<Employee> findAllEmployees() {
        List<Employee> employees;
        try {
            employees = jdbcTemplate.query(FIND_ALL_EMPLOYEES, employeeRowMapper);
            return employees;
        } catch (DataAccessException exception) {
            LOGGER.error("Error while retrieving all employees", exception);
            throw new DaoException(exception);
        }
    }

    @Override
    public List<Employee> findEmployeesByFirstNameOrLastName(String name) {
        List<Employee> employees;
        try {
            employees = jdbcTemplate.query(FIND_EMPLOYEES_BY_FIRST_OR_LAST_NAME, employeeRowMapper, name, name);
            return employees;
        } catch (DataAccessException exception) {
            LOGGER.error("Error while retrieving an employee by first or last name", exception);
            throw new DaoException(exception);
        }
    }

    @Override
    public Employee findEmployeeByEmail(String email) {
        Employee employee;
        try {
            employee = jdbcTemplate.queryForObject(FIND_EMPLOYEE_BY_EMAIL, employeeRowMapper, email);
            return employee;
        } catch (EmptyResultDataAccessException exception) {
            return null;
        } catch (DataAccessException exception) {
            LOGGER.error("Error while retrieving an employee by email", exception);
            throw new DaoException(exception);
        }
    }

    @Override
    public Employee findEmployeeById(Long id) {
        Employee employee;
        try {
            employee = jdbcTemplate.queryForObject(FIND_EMPLOYEE_BY_ID, employeeRowMapper, id);
            return employee;
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
        catch (DataAccessException exception) {
            LOGGER.error("Error while retrieving an employee by id", exception);
            throw new DaoException(exception);
        }
    }

    @Override
    public void saveEmployee(Employee employee) {
        try {
            jdbcTemplate.update(SAVE_EMPLOYEE, employee.getFirstName(), employee.getLastName(), employee.getEmail());
        } catch (DataAccessException exception) {
            LOGGER.error("Error while saving an employee", exception);
            throw new DaoException(exception);
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        try {
            jdbcTemplate.update(UPDATE_EMPLOYEE, employee.getFirstName(), employee.getLastName(),
                                                 employee.getEmail(), employee.getId());
        } catch (DataAccessException exception) {
            LOGGER.error("Error while updating an employee", exception);
            throw new DaoException(exception);
        }
    }

    @Override
    public void deleteEmployee(Long id) {
        try {
            jdbcTemplate.update(DELETE_EMPLOYEE, id);
        } catch (DataAccessException exception) {
            LOGGER.error("Error while deleting an employee", exception);
            throw new DaoException(exception);
        }
    }
}