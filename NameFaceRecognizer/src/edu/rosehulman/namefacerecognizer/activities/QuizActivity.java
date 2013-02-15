package edu.rosehulman.namefacerecognizer.activities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;
import edu.rosehulman.namefacerecognizer.R;
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
		//List<Student> students = PersistenceService.getInstance(getApplicationContext()).getAllStudents();
		List<Student> students = retrieveStudentsForQuiz();
		int numberOfQuizQuestions = getPreferredNumberOfQuestions();
		this.quiz = new Quiz(numberOfQuizQuestions, students);
		this.view.setQuiz(quiz);
		this.view.setAnswerChoices(students);
		displayNextQuestion();
	}

	private List<Student> retrieveStudentsForQuiz() {
		List<String> sectionIDs = this.getIntent().getStringArrayListExtra(MainActivity.SECTION_IDS);
		Set<Student> allStudents = new HashSet<Student>();
		for (String sectionID : sectionIDs) {
			List<Student> sectionStudents = PersistenceService.getInstance(getApplicationContext()).getStudentsForSection(sectionID);
			allStudents.addAll(sectionStudents);
		}
		List<Student> students = new ArrayList<Student>(allStudents);
		return students;
	}

	private int getPreferredNumberOfQuestions() {
		// TODO: aks the user / get from preferences
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		int num = Integer.parseInt(prefs.getString(
				getString(R.string.number_pref), "6"));
		return num;
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
		changeEvals(quiz.getAllQuestions());
	}

	private void changeEvals(List<QuizQuestion> questions) {
		double qlty;//see http://www.supermemo.com/english/ol/sm2.htm for explanation of q value
		for (int i =0; i< questions.size(); i++){
			QuizQuestion q=questions.get(i);
			if (q.getAnsweredCorrectly())
				qlty=5;
			else if (q.getSkipped())
				qlty=3.5;
			else if (!q.getAnsweredCorrectly())
				qlty=3;
			else
				continue;
			Student s=q.getStudent();
			double oldEval=s.getEValue();
			double newEval=oldEval+(0.1-(5-qlty)*(0.08+(5-qlty)*0.02));
			if (newEval<1.3)
				newEval=1.3;
			else if (newEval>2.5)
				newEval=2.5;
			System.out.println("student "+ i + " new EValue = " + newEval+ "quality = "+ qlty);

			s.setEValue(newEval);
		}
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

	}

	private void displayNextQuestion() {
		if(quiz.hasMoreQuestions()) {
			QuizQuestion question = this.quiz.getNextQuestion();
			this.currentQuestion = question;
			this.view.setDisplayedQuestion(question);
		}
		else {
			onQuitQuiz();
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
