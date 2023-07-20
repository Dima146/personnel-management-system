package com.company.managementsystem.dao;

import com.company.managementsystem.entity.Employee;

import java.util.List;

public interface EmployeeDao {

    List<Employee> findAllEmployees();

    List<Employee> findEmployeesByFirstNameOrLastName(String name);

    Employee findEmployeeByEmail(String email);

    Employee findEmployeeById(Long id);

    void saveEmployee(Employee employee);

    void updateEmployee(Employee employee);

    void deleteEmployee(Long id);
}