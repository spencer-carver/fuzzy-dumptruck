package edu.rosehulman.nameface.model;

import java.util.ArrayList;
import java.util.List;

public class Review {

	private List<Section> sectionsForReview;
	
	public Review(List<Section> sections) {
		this.sectionsForReview = sections;
	}
	
	public List<Student> getStudentsForReview() {
		List<Student> students = new ArrayList<Student>();
		for (Section section : this.sectionsForReview) {
			students.addAll(section.getStudents());
		}
		
		return students;
	}
}
