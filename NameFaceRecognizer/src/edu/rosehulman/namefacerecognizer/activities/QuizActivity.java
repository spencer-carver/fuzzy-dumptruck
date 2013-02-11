package edu.rosehulman.namefacerecognizer.activities;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;
import edu.rosehulman.namefacerecognizer.model.Quiz;
import edu.rosehulman.namefacerecognizer.model.QuizQuestion;
import edu.rosehulman.namefacerecognizer.model.Student;
import edu.rosehulman.namefacerecognizer.services.PersistenceService;
import edu.rosehulman.namefacerecognizer.views.QuizView;
import edu.rosehulman.namefacerecognizer.views.QuizView.QuizViewListener;

public class QuizActivity extends Activity implements QuizViewListener {

	private QuizView view;
	private AlertDialog alert;
	
	private Quiz quiz;
	private QuizQuestion currentQuestion;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.view = new QuizView(this, null);
		this.view.setListener(this);
		this.setContentView(view);
		List<Student> students = PersistenceService.getInstance(getApplicationContext()).getAllStudents();
		int numberOfQuizQuestions = getPreferredNumberOfQuestions();
		//TODO: Sort students by e vals
		//call dbadpater.updateStudent(updated student)
		this.quiz = new Quiz(numberOfQuizQuestions, students);
		this.view.setQuiz(quiz);
		displayNextQuestion();
	}
	
	private int getPreferredNumberOfQuestions() {
		// TODO: aks the user / get from preferences
		return 6;
	}
//
//	private void getNewImage() {
//		if (numSeen == 6) {
//			AlertDialog.Builder build = new AlertDialog.Builder(this);
//			build.setTitle("Quiz Results");
//			build.setMessage("You correctly answered:" + numCorrect + " / " + numSeen +"\nYou skipped: "+ numSkip + " / " + numSeen + "\nYou incorrectly answered: "+ numIncorrect+" / " + numSeen);
//			build.setPositiveButton("Exit Quiz",
//					new DialogInterface.OnClickListener() {
//
//						public void onClick(DialogInterface dialog, int which) {
//							alert.dismiss();
//							finish();
//						}
//
//					});
//			alert = build.create();
//			alert.show();
//		}
//		int tempindex = (int) Math.round(4 * Math.random());
//		while (tempindex == index) {
//			tempindex = (int) Math.round(4 * Math.random());
//		}
//		index = tempindex;
//		Student newStudent = mStudents.get(index);
//		byte[] imageData = PersistenceService.getInstance(getApplicationContext()).getStudentImageData(newStudent);
//		Bitmap studentImage = BitmapUtils.loadBitmap(imageData);
//		featuredImage.setImageBitmap(studentImage);
//
//	}

	public void onQuitQuiz() {
		showQuizStatistics();
	}

	public void onSkipStudent() {
		AlertDialog.Builder build = new AlertDialog.Builder(this);
		build.setTitle("Confirm Skip");
		build.setMessage("Are you sure you want to skip?");
		build.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						alert.dismiss();
						String correctAnswer = currentQuestion.getAnswer();
						Toast.makeText(QuizActivity.this,
								"That was " + correctAnswer + ".",
								Toast.LENGTH_SHORT).show();
						quiz.markSkipped(currentQuestion);
						displayNextQuestion();
					}

				});
		build.setNegativeButton("No",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface arg0, int arg1) {
						alert.dismiss();
					}

				});
		alert = build.create();
		alert.show();
		//TODO: should skipping a question change the sm2 value?
		
	}

	public void onGuess(String guessedName) {
		String correctAnswer = currentQuestion.getAnswer();
		if (guessedName.equals(correctAnswer)) {
			Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
			this.quiz.markCorrect(currentQuestion);
			displayNextQuestion();
		} else {
			Toast.makeText(this,
					"Incorrect, it was " + correctAnswer + ".",
					Toast.LENGTH_SHORT).show();
			this.quiz.markIncorrect(currentQuestion);
			displayNextQuestion();
		}
		//TODO: update sm2 value here
		
	}
	
	private void displayNextQuestion() {
		if(quiz.hasMoreQuestions()) {
			QuizQuestion question = this.quiz.getNextQuestion();
			this.currentQuestion = question;
			this.view.setDisplayedQuestion(question);
		}
		else {

			showQuizStatistics();
		}
	}
	
	private void endQuiz() {
		PersistenceService.getInstance(getApplicationContext()).persistQuizResults(this.quiz);
		finish();
	}
	
	private void showQuizStatistics() {
		AlertDialog.Builder build = new AlertDialog.Builder(this);
		build.setTitle("Quiz Results");
		build.setMessage("You correctly answered:" + quiz.getNumberOfCorrectAnswers() + " / " + quiz.getTotalQuestionsInQuiz()
				+"\nYou skipped: "+ quiz.getNumberOfSkippedQuestions() + " / " + quiz.getTotalQuestionsInQuiz() +
				"\nYou incorrectly answered: "+ quiz.getNumberOfIncorrectAnswers() +" / " + quiz.getTotalQuestionsInQuiz());
		build.setPositiveButton("Exit Quiz",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						alert.dismiss();
						endQuiz();
					}

				});
		alert = build.create();
		alert.show();

	}
}
