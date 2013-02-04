package edu.rosehulman.namefacerecognizer.model;

import android.graphics.Bitmap;

public class StudentInfo {

	private int ID;
	private Bitmap image;
	private String photoPath;
	private String firstName = "";
	private String lastName = "";
	private String nickName = "";
	private String course = "";
	private String note = "";
	
	public void setID(int id) {
		ID = id;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getName() {
		String name = firstName + " ";
		if (!nickName.equals("")) {
			name += "\" " + nickName + " \" ";
		}
		name += lastName;
		return name;
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
	
	public Bitmap getPicture() { // not actually a string, but is now for convienience
		return image;
	}
	
	public void setPicture(Bitmap picture) { // not actually a string, but is for convienience for now
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

	public void setImagePath(String path) {
		this.photoPath = path;
	}
	
	public String getImagePath() {
		return this.photoPath;
	}
}
