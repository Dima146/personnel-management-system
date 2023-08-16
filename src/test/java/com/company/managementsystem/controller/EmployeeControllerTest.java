package com.company.managementsystem.controller;

import com.company.managementsystem.Application;
import com.company.managementsystem.entity.Employee;
import com.company.managementsystem.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeServiceImpl employeeService;

    @Test
    public void EmployeeController_FindAllEmployee_ReturnEmployees() throws Exception {

        Employee employee1 = new Employee("John", "Doe", "john@mail.com");
        Employee employee2 = new Employee("Mary", "Brown", "mary@mail.com");

        when(employeeService.findAll()).thenReturn(List.of(employee1, employee2));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employees/list"))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView,"employees/list-employees");

    }

    @Test
    public void EmployeeController_SaveEmployee_Success() throws Exception {

        Employee employee = new Employee("John", "Doe", "john@mail.com");

        doNothing().when(employeeService).save(employee);


        MvcResult mvcResult = mockMvc.perform(post("/employees/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(employee)))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "employees/employee-form");

    }

    @Test
    public void EmployeeController_UpdateEmployee_Success() throws Exception {

        Employee employee = new Employee("John", "Doe", "john@mail.com");

        when(employeeService.findById(1L)).thenReturn(employee);

        MvcResult mvcResult = mockMvc.perform(post("/employees/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(employee)))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "employees/employee-update");

    }

    @Test
    public void EmployeeController_DeleteEmployee_Success() throws Exception {

        Employee employee = new Employee("John", "Doe", "john@mail.com");

        when(employeeService.findById(1L)).thenReturn(employee);

        Long employeeId = 1L;
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employees/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("employeeId", String.valueOf(employeeId)))
                        .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "redirect:/employees/list");

    }

    @Test
    public void EmployeeController_SearchEmployee_Success() throws Exception {

        Employee employee = new Employee("John", "Doe", "john@mail.com");

        when(employeeService.findByEmail("john@mail.com")).thenReturn(Optional.of(employee));

        MvcResult mvcResult = mockMvc.perform(get("/employees/search")
                .contentType(MediaType.APPLICATION_JSON)
                .param("searchName", "D"))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "employees/list-employees");

    }

    @Test
    public void EmployeeController_SaveForm_ReturnForm() throws Exception {

        MvcResult  mvcResult = mockMvc.perform(get("/employees/showFormForAdd")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "employees/employee-form");
    }

    @Test
    public void EmployeeController_UpdateForm_ReturnForm() throws Exception {

        Employee employee = new Employee(1L, "John", "Doe", "john@mail.com");

        when(employeeService.findById(1L)).thenReturn(employee);

        MvcResult mvcResult = mockMvc.perform(get("/employees/showFormForUpdate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("employeeId", String.valueOf(1L)))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(modelAndView, "employees/employee-update");
    }
}
