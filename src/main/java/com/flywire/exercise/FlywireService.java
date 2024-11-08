package com.flywire.exercise;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface FlywireService {

	List<Employee> getActiveEmployees() throws JsonParseException, JsonMappingException, IOException;

	GetEmployeeResponseDto getEmployee(int id) throws JsonParseException, JsonMappingException, IOException;

	List<Employee> getEmployeesByHireDateRange(String startDate, String endDate) throws JsonParseException, JsonMappingException, IOException;

	void addEmployee(Employee employee) throws JsonParseException, JsonMappingException, IOException;

	void deactivateEmployee(int id) throws JsonParseException, JsonMappingException, IOException;

}
