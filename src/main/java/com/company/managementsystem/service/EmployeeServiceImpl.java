package com.company.managementsystem.service;

import java.util.List;
import java.util.Optional;
import com.company.managementsystem.dao.EmployeeDao;
import com.company.managementsystem.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.managementsystem.entity.Employee;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeDao employeeDao;

	@Autowired
	public EmployeeServiceImpl(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	@Override
	public List<Employee> findAll() {
		List<Employee> employees = employeeDao.findAllEmployees();
		if (employees.isEmpty()) {
			throw new EntityNotFoundException("Employees were not found");
		}
		return employees;
	}

	@Override
	public Employee findById(Long id) {
		Employee employee = employeeDao.findEmployeeById(id);

		if (employee != null) {
			return employee;

		} else {
			throw new EntityNotFoundException("Employee was not found");
		}
	}

	@Override
	public void save(Employee employee) {
		employeeDao.saveEmployee(employee);
	}

	@Override
	public void update(Employee employee) {

		Employee existing = employeeDao.findEmployeeById(employee.getId());
		if (existing == null) {
			throw new EntityNotFoundException("Employee was not found");
		}
		employeeDao.updateEmployee(employee);
	}

	@Override
	public void deleteById(Long id) {
		Employee employee = employeeDao.findEmployeeById(id);
		if (employee == null) {
			throw new EntityNotFoundException("Employee was not found");
		}
		employeeDao.deleteEmployee(id);
	}

	@Override
	public List<Employee> searchEmployee(String name) {
		List<Employee> employees = employeeDao.findEmployeesByFirstNameOrLastName(name);

		return employees.isEmpty() ? findAll() : employees;
	}

	@Override
	public Optional<Employee> findByEmail(String email) {
		return Optional.ofNullable(employeeDao.findEmployeeByEmail(email));
	}
}