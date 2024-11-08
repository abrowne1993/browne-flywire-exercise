package com.flywire.exercise;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class FlywireServiceImpl implements FlywireService {

	public List<Employee> getActiveEmployees() throws JsonParseException, JsonMappingException, IOException{
		// Get all employees
		List<Employee> employees = getEmployees();
		
		// Remove inactive employees
		employees.removeIf(employee -> !employee.isActive());
		
		// Sort by last name
		employees.sort(Comparator.comparing(Employee::getLastName));
		
		return employees;
	}

	public GetEmployeeResponseDto getEmployee(int id) throws JsonParseException, JsonMappingException, IOException {
		// Get all employees
		List<Employee> employees = getEmployees();
		
		// Find employee with given id
		Employee foundEmployee = findEmployee(employees, id);
		
		// Throw exception if employee is null
		if (foundEmployee == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found for given id");
		}
		
		// Find employees matching ids listed as foundEmployee's directReports
		List<Employee> directHires = new ArrayList<Employee>();
		if (foundEmployee.getDirectReports() != null) {
			directHires = employees.stream()
					.filter(employee -> IntStream.of(foundEmployee.getDirectReports()).anyMatch(x -> x == employee.getId()))
					.collect(Collectors.toList());
		} 
		
		// Create a list of directHire names
		List<String> directHireNames = new ArrayList<String>();
		directHires.forEach(employee -> directHireNames.add(employee.getName()));
		
		return new GetEmployeeResponseDto(foundEmployee, directHireNames);
	}

	public List<Employee> getEmployeesByHireDateRange(String startDate, String endDate) throws JsonParseException, JsonMappingException, IOException {
		// Create DateTimeFormatter matching pattern of dates stored in json file
		DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		
		// Convert given startDate and endDate to LocalDates
		LocalDate start = LocalDate.parse(startDate, f);
		LocalDate end = LocalDate.parse(endDate, f);
		
		// Get all employees
		List<Employee> employees = getEmployees();
		
		// Find employees whose hireDate falls within start and end dates, inclusive
		List<Employee> foundEmployees = employees.stream()
				.filter(employee -> {
					LocalDate hireDate = employee.getLocalDate();
					return ((hireDate.isEqual(start) || hireDate.isAfter(start)) && 
							(hireDate.isEqual(end) || hireDate.isBefore(end)));
				})
				.collect(Collectors.toList());
		
		// Sort in order of descending hireDate (newest first)
		foundEmployees.sort(Comparator.comparing(Employee::getLocalDate, Comparator.reverseOrder()));
		
		return foundEmployees;
	}

	public void addEmployee(Employee employee) throws JsonParseException, JsonMappingException, IOException {
		// Validate input
		if (employee.getId() == 0 || employee.getName() == null || employee.getPosition() == null || employee.getHireDate() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee must have valid id, name, position, and hire date");
		}
		
		// Get all employees
		List<Employee> employees = getEmployees();
		
		// Check if employee with given id already exists (id must be unique)
		Employee foundEmployee = findEmployee(employees, employee.getId());
		
		// Throw exception if employee with given id already exists
		if (foundEmployee != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee with given id already exists");
		}
		
		// Add given employee to list
		employees.add(employee);
		
		// Write list to file
		writeFile(employees);
	}

	public void deactivateEmployee(int id) throws JsonParseException, JsonMappingException, IOException {
		// Get all employees
		List<Employee> employees = getEmployees();
		
		// Find employee with given id
		Employee foundEmployee = findEmployee(employees, id);
		
		// Throw exception if employee is null
		if (foundEmployee == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found for given id");
		}
		
		// Set foundEmployee active value to false
		employees.get(employees.indexOf(foundEmployee)).setActive(false);
		
		// Write list to file
		writeFile(employees);
	}
	
	// Helper method to read json data file and return list of all employees
	private List<Employee> getEmployees() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<Employee> employees = new ArrayList<Employee>(Arrays.asList(objectMapper.readValue(new File("src/main/resources/json/data.json"), Employee[].class)));
		return employees;
	}
	
	// Helper method to filter a list of employees and return an employee with the given id
	private Employee findEmployee(List<Employee> employees, int id) {
		Employee foundEmployee = employees.stream()
				.filter(employee -> employee.getId() == id)
				.findAny()
				.orElse(null);
		return foundEmployee;
	}
	
	// Helper method to write a given list of employees to the data file (overwrites existing file)
	private void writeFile(List<Employee> employees) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(new File("src/main/resources/json/data.json"), employees);
	}

}
