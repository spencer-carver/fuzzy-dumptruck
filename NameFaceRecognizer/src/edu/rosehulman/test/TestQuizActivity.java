package edu.rosehulman.test;

import edu.rosehulman.namefacerecognizer.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class TestQuizActivity extends Activity implements OnClickListener {

	private static final String[] STUDENTS = { "Spencer Carver", "Frank Huang",
			"Dylan Kessler", "Marina Kraeva", "Dan Schepers", "Bob Dylan",
			"Doug Schepers", "Franklin Totten", "Marie Curie" };
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
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, STUDENTS);
		nameField = (AutoCompleteTextView) findViewById(R.id.name_autocomplete_field);
		nameField.setAdapter(adapter);
		nameField.setThreshold(4);
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
			build.setPositiveButton("Yes",
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
		switch (index) {
		case 0:
			featuredImage.setImageResource(R.drawable.spencer_angel_pic);
			break;
		case 1:
			featuredImage.setImageResource(R.drawable.frank_angel_pic);
			break;
		case 2:
			featuredImage.setImageResource(R.drawable.dylan_angel_pic);
			break;
		case 3:
			featuredImage.setImageResource(R.drawable.marina_angel_pic);
			break;
		case 4:
			featuredImage.setImageResource(R.drawable.dan_angel_pic);
			break;
		default:
			// should not get here
		}

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
							Toast.makeText(TestQuizActivity.this,
									"That was " + STUDENTS[index] + ".",
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
			if (nameField.getText().toString().equals(STUDENTS[index])) {
				Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
				numCorrect++;
				numSeen++;
				getNewImage();
				nameField.setText("");
			} else {
				Toast.makeText(this,
						"Incorrect, it was " + STUDENTS[index] + ".",
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
