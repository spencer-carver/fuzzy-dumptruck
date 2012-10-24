package edu.rosehulman.namefacerecognizer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class QuizActivity extends Activity implements OnClickListener {

	private static final String[] STUDENTS = { "Spencer Carver", "Frank Huang",
			"Dylan Kessler", "Marina Kraeva", "Dan Schepers" };
	private AutoCompleteTextView nameField;
	private Button quitButton;
	private Button skipButton;
	private Button guessButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, STUDENTS);
		nameField = (AutoCompleteTextView) findViewById(R.id.name_autocomplete_field);
		nameField.setAdapter(adapter);
		nameField.setThreshold(4);
		quitButton = (Button) findViewById(R.id.quit_quiz_button);
		quitButton.setOnClickListener(this);
		skipButton = (Button) findViewById(R.id.skip_student_button);
		skipButton.setOnClickListener(this);
		guessButton = (Button) findViewById(R.id.guess_button);
		guessButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.quit_quiz_button:
			finish();
			break;
		case R.id.skip_student_button:
			
			break;
		case R.id.guess_button:
			break;
		default:
			//should not get here
		}
	}
}
