package edu.rosehulman.namefacerecognizer.model;

public class QuizQuestion {

	private boolean answeredCorrectly;
	private boolean skipped;
	private boolean seen;
	private int elapsedTime;
	private Student student;
	
	public QuizQuestion(Student student) {
		this.student = student;
		this.answeredCorrectly = false;
		this.skipped = false;
		this.seen = false;
	}
	
	public void markCorrect() {
		this.answeredCorrectly = true;
		this.skipped = false;
		this.seen = true;
		student.increaseQuizzedNumber();
		student.increaseCorrectGuesses();
	}
	
	public void markIncorrect() {
		this.answeredCorrectly = false;
		this.skipped = false;
		this.seen = true;
		student.increaseQuizzedNumber();
		student.increaseIncorrectGuesses();

	}
	
	public void markSkipped() {
		this.skipped = true;
		this.answeredCorrectly = false;
		this.seen = true;
		student.increaseQuizzedNumber();
		student.increaseNumberOfSkips();
	}
	
	public boolean wasShown() {
		return this.seen;
	}
	
	public boolean getSkipped() {
		return this.skipped;
	}
	
	public boolean getAnsweredCorrectly() {
		return this.answeredCorrectly;
	}
	
	public String getAnswer() {
		return student.getName();
	}
	
	public Student getStudent() {
		return this.student;
	}
}
