package com.company.managementsystem.controller;

import com.company.managementsystem.dto.EmployeeDto;
import com.company.managementsystem.dto.converter.DtoConverter;
import com.company.managementsystem.entity.Employee;
import com.company.managementsystem.service.EmployeeService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
	private final DtoConverter<Employee, EmployeeDto> employeeDtoConverter;
	private final EmployeeService employeeService;

	@Autowired
	public EmployeeController(DtoConverter<Employee, EmployeeDto> employeeDtoConverter, EmployeeService employeeService) {
		this.employeeDtoConverter = employeeDtoConverter;
		this.employeeService = employeeService;
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/list")
	public String listEmployees(Model model) {

		List<Employee> employeeList = employeeService.findAll();
		List<EmployeeDto> employees =
				employeeList
						.stream()
						.map(employeeDtoConverter::convertToDto)
						.collect(Collectors.toList());

		model.addAttribute("employees", employees);
		return "employees/list-employees";

	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {

		EmployeeDto employeeDto = new EmployeeDto();
		model.addAttribute("employeeDto", employeeDto);

		return "employees/employee-form";
	}

	@PostMapping("/save")
	public String saveEmployee(@Valid EmployeeDto employeeDto,
							   BindingResult bindingResult, RedirectAttributes redirectAttributes,
							   Model model) {


		if (bindingResult.hasErrors()) {
			return "employees/employee-form";
		}

		String email = employeeDto.getEmail();
		Optional<Employee> existingEmployee = employeeService.findByEmail(email);
		if (existingEmployee.isPresent()) {
			model.addAttribute("employeeDto", new EmployeeDto());
			model.addAttribute("addingEmployeeError", "Employee with this email already exists");

			return "employees/employee-form";
		}

		Employee employee = employeeDtoConverter.convertToEntity(employeeDto);
		employeeService.save(employee);
		LOGGER.info("The employee with email: " + email + " has been successfully saved");
		redirectAttributes.addFlashAttribute("added", "Employee with email: " + email
					+ " has been successfully added");

		return "redirect:/employees/list";

	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") Long id, Model model) {

		Employee employee = employeeService.findById(id);
		EmployeeDto employeeDto = employeeDtoConverter.convertToDto(employee);
		model.addAttribute("employeeDto", employeeDto);

		return "employees/employee-update";
	}

	@PostMapping("/update")
	public String updateEmployee(@Valid @ModelAttribute EmployeeDto employeeDto, BindingResult bindingResult,
								 RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return "employees/employee-update";
		}

		Employee employee = employeeDtoConverter.convertToEntity(employeeDto);

		employeeService.update(employee);
		LOGGER.info("Employee has been successfully updated");
		redirectAttributes.addFlashAttribute("updated", "Employee has been successfully updated");

		return "redirect:/employees/list";

	}

	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId") Long id, RedirectAttributes redirectAttributes) {
		employeeService.deleteById(id);
		LOGGER.info("Employee with id: " + id + " has been successfully deleted");
		redirectAttributes.addFlashAttribute("deleted", "Employee has been successfully deleted");

		return "redirect:/employees/list";
	}

	@GetMapping("/search")
	public String searchEmployee(@RequestParam(value = "searchName", required = false) String searchName,
								 Model model) {

		List<Employee> employeeList= employeeService.searchEmployee(searchName);
		List<EmployeeDto> employees =
				employeeList
						.stream()
						.map(employeeDtoConverter::convertToDto)
						.collect(Collectors.toList());

		model.addAttribute("employees", employees);
		return "employees/list-employees";

	}
}