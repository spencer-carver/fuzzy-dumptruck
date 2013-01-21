package edu.rosehulman.nameface.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Professor {
	private int professorID;
	private String username;
	private String firstName;
	private String lastName;
	
	private List<Section> assignedSections;
	
	public Professor() {
		assignedSections = new ArrayList<Section>();
	}
	
	public int getId() {
		return this.professorID;
	}
	
	public void setId(int id) {
		this.professorID = id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void addSection(Section section) {
		this.assignedSections.add(section);
	}
	
	/**
	 * Retrieves all the sections that are taught by this professor.
	 * @return An unmodifiable list
	 */
	public List<Section> getAssignedSections() {
		return Collections.unmodifiableList(assignedSections);
	}
}
