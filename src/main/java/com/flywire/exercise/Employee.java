package com.flywire.exercise;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {

	private boolean active;
	private int[] directReports;
	private String hireDate;
	private int id;
	private String name;
	private String position;
	
	@JsonCreator
	public Employee(
			@JsonProperty("active")
			boolean active, 
			@JsonProperty("directReports")
			int[] directReports, 
			@JsonProperty("hireDate")
			String hireDate, 
			@JsonProperty("id")
			int id, 
			@JsonProperty("name")
			String name, 
			@JsonProperty("position")
			String position) {
		this.active = active;
		this.directReports = directReports;
		this.hireDate = hireDate;
		this.id = id;
		this.name = name;
		this.position = position;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int[] getDirectReports() {
		return directReports;
	}

	public void setDirectReports(int[] directReports) {
		this.directReports = directReports;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@JsonIgnore
	public String getLastName() {
		return name.split(" ")[1];
	}
	
	@JsonIgnore
	public LocalDate getLocalDate() {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		return LocalDate.parse(hireDate, f);
	}
}
