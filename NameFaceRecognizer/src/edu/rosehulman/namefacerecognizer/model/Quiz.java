package edu.rosehulman.namefacerecognizer.model;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

	private List<QuizQuestion> quizQuestions;
	private int numberOfQuestions;
	private int numberAnsweredQuestions;
	private int numberCorrectAnswered;
	private int numberSkipped;
	private int numberIncorrectAnswered;
	private int totalTime;
	private int nextQuestionIndex;
	
	public Quiz(int numberOfQuestions, List<Student> students) {
		this.numberOfQuestions = numberOfQuestions;
		this.numberCorrectAnswered = 0;
		this.numberIncorrectAnswered = 0;
		this.numberSkipped = 0;
		this.nextQuestionIndex = 0;
		this.quizQuestions = new ArrayList<QuizQuestion>();
		generateQuiz(numberOfQuestions, students);
	}
	
	private void generateQuiz(int numberOfQuestions, List<Student> students) {
		int totalStudents = students.size();
		if (totalStudents < numberOfQuestions) {
			this.numberOfQuestions = totalStudents;
		}
		this.quizQuestions = new ArrayList<QuizQuestion>(this.numberOfQuestions);
		//implement ordering by sm2 values here
		// TODO: Generate quiz questions according to the algorithm
		// and put them in this.quizQuestions
		
		// DUMMY quiz generation

		System.out.print("Chosen students: ");
		System.out.print("Chosen students: ");
		for (int i=0; i< this.numberOfQuestions; i++) {
			//int index = (int)(Math.random()*totalStudents);
			System.out.print(i + ", ");
			QuizQuestion nextQuestion = new QuizQuestion(students.get(i));
			quizQuestions.add(nextQuestion);
		}
		System.out.println();
		
	}
	
	public boolean hasMoreQuestions() {
		return nextQuestionIndex < numberOfQuestions;
	}
	
	public QuizQuestion getNextQuestion() {
		return quizQuestions.get(nextQuestionIndex++);
	}
	
	public List<QuizQuestion> getAllQuestions() {
		return this.quizQuestions;
	}
	
	public List<String> getAllAnswers() {
		List<String> result = new ArrayList<String>();
		for (QuizQuestion question : quizQuestions) {
			result.add(question.getAnswer());
		}
		
		return result;
	}
	
	public int getNumberOfQuestionsLeft() {
		return numberOfQuestions - numberAnsweredQuestions;
	}
	
	public void markSkipped(QuizQuestion question) {
		this.numberSkipped++;
		this.numberAnsweredQuestions++;
		question.markSkipped();
	}
	
	public void markCorrect(QuizQuestion question) {
		question.markCorrect();
		this.numberCorrectAnswered++;
		this.numberAnsweredQuestions++;
	}
	
	public void markIncorrect(QuizQuestion question) {
		question.markIncorrect();
		this.numberIncorrectAnswered++;
		this.numberAnsweredQuestions++;
	}
	
	public int getNumberOfCorrectAnswers() {
		return this.numberCorrectAnswered;
	}
	
	public int getNumberOfSkippedQuestions() {
		return this.numberSkipped;
	}
	
	public int getTotalQuestionsInQuiz() {
		return this.numberOfQuestions;
	}
	
	public int getNumberOfIncorrectAnswers() {
		return this.numberIncorrectAnswered;
	}
}
