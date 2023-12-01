package com.company.managementsystem.service;

import java.util.List;
import java.util.Optional;
import com.company.managementsystem.entity.Employee;


public interface EmployeeService {

	List<Employee> findAll();

	List<Employee> searchEmployee(String name);

	Optional<Employee> findByEmail(String email);

	Employee findById(Long id);

	void save(Employee employee);

	void update(Employee employee);

	void deleteById(Long id);

}
