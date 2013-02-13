package edu.rosehulman.namefacerecognizer.views;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import edu.rosehulman.namefacerecognizer.R;
import edu.rosehulman.namefacerecognizer.model.Quiz;
import edu.rosehulman.namefacerecognizer.model.QuizQuestion;
import edu.rosehulman.namefacerecognizer.model.Student;
import edu.rosehulman.namefacerecognizer.services.PersistenceService;
import edu.rosehulman.namefacerecognizer.utils.BitmapUtils;

public class QuizView extends RelativeLayout {

	private Quiz quiz;
	private QuizQuestion displayedQuestion;
	private QuizViewListener listener; 
	
	private ImageView featuredImage;
	private AutoCompleteTextView nameField;
	private Button quitButton;
	private Button skipButton;
	private Button guessButton;
	private ProgressBar progressBar;
	
	public static interface QuizViewListener {
		public void onQuitQuiz();
		public void onSkipStudent();
		public void onGuess(String guessedName);
	}
	
	public QuizView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = View.inflate(context, R.layout.activity_quiz, null);
		this.addView(view);
		
		nameField = (AutoCompleteTextView) view.findViewById(R.id.name_autocomplete_field);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>( context,
				android.R.layout.simple_dropdown_item_1line, new String[] {});
		nameField.setAdapter(adapter);
		nameField.setThreshold(3);

		featuredImage = (ImageView) view.findViewById(R.id.featured_quiz_picture);
		quitButton = (Button) view.findViewById(R.id.quit_quiz_button);
		quitButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				listener.onQuitQuiz();
			}
		});
		
		skipButton = (Button) view.findViewById(R.id.skip_student_button);
		skipButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				listener.onSkipStudent();
			}
		});
		
		guessButton = (Button) view.findViewById(R.id.guess_button);
		guessButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				listener.onGuess(nameField.getText().toString());
			}
		});
	
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
		progressBar.setIndeterminate(false);
		progressBar.setProgress(1);
	}

	public void setListener(QuizViewListener listener) {
		this.listener = listener;
	}
	
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
		progressBar.setMax(quiz.getTotalQuestionsInQuiz());
		Set<String> uniqueAnswers = new HashSet<String>(quiz.getAllAnswers());
		ArrayAdapter<String> adapter = new ArrayAdapter<String>( getContext(),
				android.R.layout.simple_dropdown_item_1line,  uniqueAnswers.toArray(new String[0]));

		nameField.setAdapter(adapter);
	}
	
	public void setDisplayedQuestion(QuizQuestion question) {
		this.displayedQuestion = question;
		Student newStudent = question.getStudent();
		byte[] pictureData = PersistenceService.getInstance(getContext()).getStudentImageData(newStudent);
		Bitmap studentBitmap = BitmapUtils.loadBitmap(pictureData);
		featuredImage.setImageBitmap(studentBitmap);
		nameField.setText("");
		progressBar.incrementProgressBy(1);
	}
}
