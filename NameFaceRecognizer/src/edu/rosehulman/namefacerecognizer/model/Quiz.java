package edu.rosehulman.namefacerecognizer.model;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

	private List<Enrollment> sections;
	private List<Student> students;
	private int numberOfQuestions;
	private int numberCorrectAnswered;
	private int numberSkipped;
	private int totalTime;
	
	public Quiz(int numberOfQuestions, List<Enrollment> sections) {
		this.numberOfQuestions = numberOfQuestions;
		this.numberCorrectAnswered = 0;
		this.numberSkipped = 0;
		this.sections = sections;
	}
	
	public List<QuizQuestion> generateQuiz() {
		// List of all students
//		List<Student> students = new ArrayList<Student>();
//		for (Enrollment section : sections) {
//			students.addAll(section.getStudents());
//		}

		List<QuizQuestion> quizQuesitons = new ArrayList<QuizQuestion>(numberOfQuestions);
		// TODO: Generate quiz questions according to the algorithm
		return quizQuesitons;
	}
}
