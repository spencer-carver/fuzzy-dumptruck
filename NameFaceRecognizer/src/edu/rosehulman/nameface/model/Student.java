package edu.rosehulman.nameface.model;

import android.graphics.Bitmap;

public class Student {
	
	private StudentInfo personalInfo;
	private int numGuessedCorrect;
	private int numGuessedTotal;
	
	public void setStudentInfo(StudentInfo info) {
		this.personalInfo = info;
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
	
	public double getCorrectGuessesRatio() {
		return this.numGuessedCorrect / (double)this.numGuessedTotal;
	}
	
	public int getID() {
		return this.personalInfo.getID();
	}
	
	public String getName() {
		return this.personalInfo.getName();
	}
	
	public String getFirstName() {
		return this.personalInfo.getFirstName();
	}
	
	public String getLastName() {
		return this.personalInfo.getLastName();
	}
	
	public String getNickName() {
		return this.personalInfo.getNickName();
	}
	
	public void setNickName(String nname) {
		this.personalInfo.setNickName(nname);
	}
	
	public Bitmap getPicture() {
		return this.personalInfo.getPicture();
	}
	
	public String getNote() {
		return this.personalInfo.getNote();
	}
	
	public void setNote(String new_note) {
		this.personalInfo.setNote(new_note);
	}
	
	public String getCourse() {
		return this.personalInfo.getCourse();
	}
	
}
