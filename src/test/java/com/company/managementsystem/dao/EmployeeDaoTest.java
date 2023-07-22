package com.company.managementsystem.dao;

import com.company.managementsystem.dao.rowmapper.EmployeeRowMapper;
import com.company.managementsystem.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {EmployeeDaoImpl.class, EmployeeRowMapper.class})
public class EmployeeDaoTest {

    @Autowired
    private EmployeeDaoImpl employeeDao;


    @Test
    public void EmployeeDao_FindAll_ReturnAllEmployees() {

        Employee employee1 = new Employee("John", "Doe", "john@mail.com");
        Employee employee2 = new Employee("Mary", "Brown", "mary@mail.com");

        employeeDao.saveEmployee(employee1);
        employeeDao.saveEmployee(employee2);

        List<Employee> employees = employeeDao.findAllEmployees();

        assertThat(employees).isNotNull();

    }

    @Test
    public void EmployeeDao_FindByEmail_ReturnEmployee() {

        Employee employee = new Employee("John", "Doe", "john@mail.com");
        employeeDao.saveEmployee(employee);

        Employee result = employeeDao.findEmployeeByEmail(employee.getEmail());

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(employee.getEmail());

    }

    @Test
    public void EmployeeDao_FindByFirstOrLastName_ReturnEmployee() {

        Employee employee = new Employee("John", "Doe", "john@mail.com");
        employeeDao.saveEmployee(employee);

        List<Employee> result =
                employeeDao.findEmployeesByFirstNameOrLastName("John");


        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);

    }

    @Test
    public void EmployeeDao_Delete_SuccessfullyDeleted() {

        Employee employee = new Employee(1L, "John", "Doe", "john@mail.com");
        employeeDao.saveEmployee(employee);

        employeeDao.deleteEmployee(employee.getId());

        Employee result = employeeDao.findEmployeeById(employee.getId());
        assertThat(result).isNull();

    }

    @Test
    public void EmployeeDao_Save_ReturnEmployee() {

        Employee employee = new Employee( "John", "Doe", "john@mail.com");
        employeeDao.saveEmployee(employee);

        Employee result = employeeDao.findEmployeeById(1L);

        assertThat(result).isNotNull();

    }
}