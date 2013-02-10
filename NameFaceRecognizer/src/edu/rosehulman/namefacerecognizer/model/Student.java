package edu.rosehulman.namefacerecognizer.model;


public class Student {
	
	private StudentInfo personalInfo;
	private int numGuessedCorrect;
	private int numGuessedTotal;
	
	public Student () {
		this.personalInfo = new StudentInfo();
	}
	
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
	
	public String getNote() {
		return this.personalInfo.getNote();
	}
	
	public void setNote(String new_note) {
		this.personalInfo.setNote(new_note);
	}
	

	public void setFirstName(String firstName) {
		this.personalInfo.setFirstName(firstName);
	}
	
	public void setLastName(String lastName) {
		this.personalInfo.setLastName(lastName);
	}
	
	public void setUsername(String username) {
		this.personalInfo.setUsername(username);
	}
	
	public String getUsername() {
		return this.personalInfo.getUsername();
	}
	
	public void setImagePath(String path) {
		this.personalInfo.setImagePath(path);
	}
	
	public String getImagePath() {
		return this.personalInfo.getImagePath();
	}

	public void setID(int id) {
		this.personalInfo.setID(id);
	}

	public void increaseQuizzedNumber() {
		// TODO Auto-generated method stub
		
	}

	public void increaseNumberOfSkips() {
		// TODO Auto-generated method stub
		
	}

	public void increaseIncorrectGuesses() {
		// TODO Auto-generated method stub
		
	}

	public void increaseCorrectGuesses() {
		// TODO Auto-generated method stub
		
	}

}
 