package edu.rosehulman.namefacerecognizer.model;

public class QuizQuestion {

	private boolean answeredCorrectly;
	private boolean skipped;
	private int elapsedTime;
	private Student student;
	
	public QuizQuestion(Student student) {
		this.student = student;
		this.answeredCorrectly = false;
		this.skipped = false;
	}
	
	public void markCorrect() {
		this.answeredCorrectly = true;
		this.skipped = false;
	}
	
	public void markIncorrect() {
		this.answeredCorrectly = false;
		this.skipped = false;
	}
	
	public void markSkipped() {
		this.skipped = true;
	}
	
	public boolean getSkipped() {
		return this.skipped;
	}
	
	public boolean getAnsweredCorrectly() {
		return this.answeredCorrectly;
	}
}
