package edu.rosehulman.namefacerecognizer.model;

import java.util.ArrayList;
import java.util.List;

public class Review {

	private List<Enrollment> sectionsForReview;
	private List<Student> studentsForReview;
	
	public Review(List<Enrollment> sections) {
		this.studentsForReview = new ArrayList<Student>();
		this.sectionsForReview = sections;
	}
	
	public Review() {
		this.sectionsForReview = new ArrayList<Enrollment>();
		this.studentsForReview = new ArrayList<Student>();
	}
	
	public List<Student> getStudentsForReview() {
//		List<Student> students = new ArrayList<Student>();
//		for (Enrollment section : this.sectionsForReview) {
//			students.addAll(section.getStudents());
//		}
//		
//		return students;
		return this.studentsForReview;
	}
	
	public void addSection(Enrollment section) {
		if(!this.sectionsForReview.contains(section)) {			
			this.sectionsForReview.add(section);
		}
	}
	
	public void addStudents(List<Student> students) {
		this.studentsForReview.addAll(students);
	}
}
