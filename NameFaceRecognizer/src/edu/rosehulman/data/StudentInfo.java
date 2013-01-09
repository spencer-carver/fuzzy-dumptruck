package edu.rosehulman.data;

public class StudentInfo {

	private int ID;
	private String image;
	private String firstName;
	private String lastName;
	private String nickName;
	private String course;
	private String note;
	private int numGuessedCorrect;
	private int numGuessedTotal;
	
	public void setID(int id) {
		ID = id;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String fname) {
		firstName = fname;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lname) {
		lastName = lname;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public void setNickName(String nname) {
		nickName = nname;
	}
	
	public String getPicture() { // not actually a string, but is now for convienience
		return image;
	}
	
	public void setPicture(String picture) { // not actually a string, but is for convienience for now
		image = picture;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String new_note) {
		note = new_note;
	}
	
	public String getCourse() {
		return course;
	}
	
	public void setCourse(String new_course) {
		course = new_course;
	}
	
	public int getNumGuessed() {
		return numGuessedCorrect;
	}
	
	public int getNumTotal() {
		return numGuessedTotal;
	}
	
	public void setNumGuessed(int num) {
		numGuessedCorrect = num;
	}
	
	public void setNumTotal(int num) {
		numGuessedTotal = num;
	}
}
