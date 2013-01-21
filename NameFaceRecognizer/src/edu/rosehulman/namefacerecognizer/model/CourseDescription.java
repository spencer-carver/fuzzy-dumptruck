package edu.rosehulman.namefacerecognizer.model;

public class CourseDescription {
	
	private String id;
	private String courseName;
	private String quaterOffered;
	private String courseDescription;
	
	public CourseDescription(String id, String courseName, String quaterOffered, String courseDescription) {
		this.id = id;
		this.courseName = courseName;
		this.quaterOffered = quaterOffered;
		this.courseDescription = courseDescription;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public String getQuaterOffered() {
		return quaterOffered;
	}
	public void setQuaterOffered(String quaterOffered) {
		this.quaterOffered = quaterOffered;
	}
	
	public String getCourseDescription() {
		return courseDescription;
	}
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
	
	
}
