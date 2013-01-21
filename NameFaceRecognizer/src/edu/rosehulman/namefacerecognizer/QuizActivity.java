package edu.rosehulman.namefacerecognizer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import edu.rosehulman.data.DBAdapter;
import edu.rosehulman.nameface.model.Student;

public class QuizActivity extends Activity implements OnClickListener {

	private List<Student> mStudents;
	private DBAdapter mDBAdapter;
	private ImageView featuredImage;
	private AutoCompleteTextView nameField;
	private Button quitButton;
	private Button skipButton;
	private Button guessButton;
	private int index = 0;
	private AlertDialog alert;
	private int numSeen = 0;
	private int numCorrect = 0;
	private int numIncorrect = 0;
	private int numSkip = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		mDBAdapter = new DBAdapter(this);
		mDBAdapter.open();
		mStudents = mDBAdapter.getAllStudents(); // TODO replace with settings later
		List<String> STUDENTS = new ArrayList<String>();
		for (Student student : mStudents) {
			STUDENTS.add(student.getName());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, STUDENTS.toArray( new String[] {} ));
		nameField = (AutoCompleteTextView) findViewById(R.id.name_autocomplete_field);
		nameField.setAdapter(adapter);
		nameField.setThreshold(3);
		featuredImage = (ImageView) findViewById(R.id.featured_quiz_picture);
		getNewImage();
		quitButton = (Button) findViewById(R.id.quit_quiz_button);
		quitButton.setOnClickListener(this);
		skipButton = (Button) findViewById(R.id.skip_student_button);
		skipButton.setOnClickListener(this);
		guessButton = (Button) findViewById(R.id.guess_button);
		guessButton.setOnClickListener(this);
	}

	private void getNewImage() {
		if (numSeen == 6) {
			AlertDialog.Builder build = new AlertDialog.Builder(this);
			build.setTitle("Quiz Results");
			build.setMessage("You correctly answered:" + numCorrect + " / " + numSeen +"\nYou skipped: "+ numSkip + " / " + numSeen + "\nYou incorrectly answered: "+ numIncorrect+" / " + numSeen);
			build.setPositiveButton("Exit Quiz",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							alert.dismiss();
							finish();
						}

					});
			alert = build.create();
			alert.show();
		}
		int tempindex = (int) Math.round(4 * Math.random());
		while (tempindex == index) {
			tempindex = (int) Math.round(4 * Math.random());
		}
		index = tempindex;
		featuredImage.setImageBitmap(mStudents.get(index).getPicture());

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.quit_quiz_button:
			finish();
			break;
		case R.id.skip_student_button:
			AlertDialog.Builder build = new AlertDialog.Builder(this);
			build.setTitle("Confirm Skip");
			build.setMessage("Are you sure you want to skip?");
			build.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							alert.dismiss();
							Toast.makeText(QuizActivity.this,
									"That was " + mStudents.get(index).getName() + ".",
									Toast.LENGTH_SHORT).show();
							numSkip++;
							numSeen++;
							getNewImage();
							nameField.setText("");
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
			break;
		case R.id.guess_button:
			if (nameField.getText().toString().equals(mStudents.get(index).getName())) {
				Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
				numCorrect++;
				numSeen++;
				getNewImage();
				nameField.setText("");
			} else {
				Toast.makeText(this,
						"Incorrect, it was " + mStudents.get(index).getName() + ".",
						Toast.LENGTH_SHORT).show();
				numIncorrect++;
				numSeen++;
				getNewImage();
				nameField.setText("");
			}
			break;
		default:
			// should not get here
		}
	}
}
