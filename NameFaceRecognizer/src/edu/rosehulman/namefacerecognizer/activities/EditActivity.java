package edu.rosehulman.namefacerecognizer.activities;

import edu.rosehulman.namefacerecognizer.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class EditActivity extends Activity {
	
	private ImageView mPicture;
	private EditText mName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_student);
		mPicture = (ImageView) findViewById(R.id.edit_picture);
		mName = (EditText) findViewById(R.id.edit_student_name);
	}

}
