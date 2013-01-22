package edu.rosehulman.namefacerecognizer.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Section {

	private CourseDescription courseDescription;
	private String sectionID;
	private String term;
	private String cid;
	private String professorID;
	private List<Student> students;
	
	public Section(String sectionID, String term, String cid, String professorID) {
		this.sectionID = sectionID;
		this.term = term;
		this.cid = cid;
		this.professorID = professorID;
		this.students = new ArrayList<Student>();
	}
	
	public String getCourseID() {
		return courseDescription.getId();
	}
	
	public List<Student> getStudents() {
		return Collections.unmodifiableList(students);
	}
	
	public void addStudent(Student student) {
		students.add(student);
	}
	
	public void addStudents(List<Student> newStudents) {
		this.students.addAll(newStudents);
	}
}
