package edu.rosehulman.namefacerecognizer.model;

import java.util.ArrayList;
import java.util.List;

public class Review {

	private List<Section> sectionsForReview;
	
	public Review(List<Section> sections) {
		this.sectionsForReview = sections;
	}
	
	public Review() {
		this.sectionsForReview = new ArrayList<Section>();
	}
	
	public List<Student> getStudentsForReview() {
		List<Student> students = new ArrayList<Student>();
		for (Section section : this.sectionsForReview) {
			students.addAll(section.getStudents());
		}
		
		return students;
	}
	
	public void addSection(Section section) {
		if(!this.sectionsForReview.contains(section)) {			
			this.sectionsForReview.add(section);
		}
	}
}
