package com.flywire.exercise;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class HelloWorldController
{
	
  private final FlywireService flywireService;
  
  public HelloWorldController(FlywireService flywireService) {
	  this.flywireService = flywireService;
  }

  @RequestMapping(value = "/", method = { RequestMethod.GET })
  public String healthCheck()
  {
    return "Hello world!";
  }
  
  // Returns a list of all active employees in alphabetical order of last name
  @GetMapping(value = "/getActiveEmployees")
  public List<Employee> getActiveEmployees() throws JsonParseException, JsonMappingException, IOException {
	  return flywireService.getActiveEmployees();
  }
  
  // Takes in an ID and returns a JSON response of the matching employees, as well as the names of their direct hires
  @GetMapping(value = "/getEmployee")
  public GetEmployeeResponseDto getEmployee(@RequestParam int id) throws JsonParseException, JsonMappingException, IOException {
	  return flywireService.getEmployee(id);
  }
  
  // Takes a date range, and returns a JSON response of all employees hired in that date range in descending order of date hired
  @GetMapping(value = "/getEmployeesByHireDateRange") 
  public List<Employee> getEmployeesByHireDateRange(@RequestParam String startDate, @RequestParam String endDate) throws JsonParseException, JsonMappingException, IOException {
	  return flywireService.getEmployeesByHireDateRange(startDate, endDate);
  }
  
  // Takes a name, id, position, direct reports, and manager to create a new employee
  @PostMapping(value = "/addEmployee")
  public void addEmployee(@RequestBody Employee employee) throws JsonParseException, JsonMappingException, IOException {
	  flywireService.addEmployee(employee);
  }
  
  // Takes in an ID and deactivates an employee
  @PutMapping(value = "/deactivateEmployee")
  public void deactivateEmployee(@RequestParam int id) throws JsonParseException, JsonMappingException, IOException {
	  flywireService.deactivateEmployee(id);
  }
}
