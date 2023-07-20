package com.company.managementsystem.service;

import com.company.managementsystem.entity.Employee;
import com.company.managementsystem.dao.EmployeeDaoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeDaoImpl employeeDao;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void EmployeeService_FindById_ReturnEmployee() {

        Employee employee = new Employee("John", "Doe", "john@mail.com");
        when(employeeDao.findEmployeeById(1L)).thenReturn(employee);

        Employee savedEmployee = employeeService.findById(1L);

        assertThat(savedEmployee).isNotNull();
    }

    @Test
    public void EmployeeService_Save_ReturnEmployee() {

        Employee employee = new Employee("Walter", "White", "walter@mail.com");
        when(employeeDao.findEmployeeByEmail("walter@mail.com")).thenReturn(employee);

        employeeService.save(employee);

        Optional<Employee> result = employeeService.findByEmail("walter@mail.com");
        assertThat(result.get().getFirstName()).isEqualTo(employee.getFirstName());

    }


    @Test
    public void Employee_Service_Update_ReturnEmployee() {

        Employee employee = new Employee(1L, "John", "Doe", "john@mail.com");
        when(employeeDao.findEmployeeById(1L)).thenReturn(employee);

        employee.setFirstName("James");
        employeeService.update(employee);

        Employee result = employeeService.findById(1L);
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(employee.getEmail());

    }

    @Test
    public void Employee_Service_Delete_ReturnNull() {

        Employee employee = new Employee(1L, "John", "Doe", "john@mail.com");
        when(employeeDao.findEmployeeById(1L)).thenReturn(employee);

        assertAll(() -> employeeService.deleteById(1L));

    }

    @Test
    public void EmployeeService_FindAll_ReturnAllEmployees() {

        Employee employee1 = new Employee("John", "Doe", "john@mail.com");
        Employee employee2 = new Employee("Mary", "Brown", "mary@mail.com");

        when(employeeDao.findAllEmployees()).thenReturn(List.of(employee1, employee2));

        List<Employee> result = employeeService.findAll();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);

    }

    @Test
    public void EmployeeService_SearchByFirstOrLastName_ReturnEmployee() {

        Employee employee1 = new Employee("John", "Doe", "john@mail.com");

        when(employeeDao.findEmployeesByFirstNameOrLastName("J"))
                .thenReturn(List.of(employee1));

        List<Employee> result = employeeService.searchEmployee("J");

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);

    }

    @Test
    public void EmployeeService_FindByEmail_ReturnEmployee() {

        Employee employee1 = new Employee("John", "Doe", "john@mail.com");
        when(employeeDao.findEmployeeByEmail("john@mail.com")).thenReturn(employee1);

        Optional<Employee> result = employeeService.findByEmail("john@mail.com");

        assertThat(result).isNotEmpty();
        assertThat(result.get().getEmail()).isEqualTo("john@mail.com");

    }
}