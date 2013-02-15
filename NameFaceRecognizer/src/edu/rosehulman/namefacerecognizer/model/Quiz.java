package edu.rosehulman.namefacerecognizer.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
		List<Student> studentsForQuiz = getSMdist(students);

		System.out.print("Chosen students: ");
		for (int i=0; i< this.numberOfQuestions; i++) {
			//int index = (int)(Math.random()*totalStudents);
			System.out.print(i + ", ");
			QuizQuestion nextQuestion = new QuizQuestion(studentsForQuiz.get(i));
			quizQuestions.add(nextQuestion);
		}
		System.out.println();
		
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 * @param students
	 * @param numberOfQuizQuestions 
	 * @return
	 */
	private List<Student> getSMdist(List<Student> students) {
		double totalSum = 0;
		for (int i = 0; i <students.size(); i ++){
			totalSum += 1.0 / students.get(i).getEValue(); // smaller e-value means better chances of picking this student (low e-value means worse knowledge)
		}
		Random r = new Random();
		List<Student> selectedStudents= new ArrayList<Student>();

		for (int i =0; i <this.numberOfQuestions; i ++){
			double randomValue = totalSum * r.nextDouble();
			System.out.println("For student " + i + " random is " + randomValue + " out of " + totalSum);
			double currentSum=0;
			int j=0;
			while (currentSum < randomValue){
				currentSum += 1.0 / students.get(j++).getEValue();
			}
			selectedStudents.add(students.get(j-1));
		}
		
		return selectedStudents;
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
