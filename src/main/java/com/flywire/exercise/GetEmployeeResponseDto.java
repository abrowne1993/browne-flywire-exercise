package com.flywire.exercise;

import java.util.List;

public class GetEmployeeResponseDto {

	private Employee employee;
	private List<String> directHires;
	
	public GetEmployeeResponseDto(Employee employee, List<String> directHires) {
		this.employee = employee;
		this.directHires = directHires;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<String> getDirectHires() {
		return directHires;
	}

	public void setDirectHires(List<String> directHires) {
		this.directHires = directHires;
	}
	
}
