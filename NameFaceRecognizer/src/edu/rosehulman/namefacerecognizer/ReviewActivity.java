package edu.rosehulman.namefacerecognizer;

import java.util.List;

import edu.rosehulman.data.DBAdapter;
import edu.rosehulman.nameface.model.Student;
import edu.rosehulman.nameface.model.StudentInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ReviewActivity extends Activity implements OnClickListener {

	private ImageView mainImage;
	private int studentID;
	private TextView studentName;
	private TextView studentCourse;
	private LinearLayout filmstrip;
	private List<Student> mStudents;
	private DBAdapter mDBAdapter;
	
	private String KEY_STUDENT_ID = "id";
	private int REQ_GOTO_EDIT = 2;
	private int filmstrip_dimension = 150;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review);
		mainImage = (ImageView) findViewById(R.id.featured_picture);
		studentName = (TextView) findViewById(R.id.student_name);
		studentCourse = (TextView) findViewById(R.id.student_course);
		filmstrip = (LinearLayout) findViewById(R.id.filmstrip);
		// Open DB and pull in list of courses
		mDBAdapter = new DBAdapter(this);
		mDBAdapter.open();
		mStudents = mDBAdapter.getAllStudents();  // For demo purposes.  In actual app it will be courses first
		for (Student student : mStudents) {
			ImageView sView = new ImageView(this);
			sView.setId(student.getID()); // TODO get working
			sView.setLayoutParams(new LinearLayout.LayoutParams(filmstrip_dimension, filmstrip_dimension));
			sView.setImageBitmap(student.getPicture());
			sView.setOnClickListener(this);
			filmstrip.addView(sView);
		}
		Student defaultStudent = mStudents.get(0);
		studentID = defaultStudent.getID();
		mainImage.setImageBitmap(defaultStudent.getPicture());
		String name = defaultStudent.getFirstName() + " ";
		if (!defaultStudent.getNickName().equals("")) {
			name += "\" " + defaultStudent.getNickName() + " \" ";
		}
		name += defaultStudent.getLastName();
		studentName.setText(name);
		studentCourse.setText(defaultStudent.getCourse());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_review, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == R.id.review_menu_edit) {
			Toast.makeText(this, "Clicked Edit", Toast.LENGTH_LONG).show();
			//TODO fix later if need be
			Intent intent = new Intent(this, EditActivity.class);
			intent.putExtra(KEY_STUDENT_ID, studentID);
			startActivityForResult(intent, REQ_GOTO_EDIT);
		}
		return false;
	}

	public void onClick(View v) {
		Student mStudent = new Student();
		for (Student student : mStudents) {
			if (student.getID() == v.getId()) {
				mStudent = student;
			}
		}
		mainImage.setImageBitmap(mStudent.getPicture());
		String name = mStudent.getFirstName() + " ";
		if (!mStudent.getNickName().equals("")) {
			name += "\" " + mStudent.getNickName() + " \" ";
		}
		name += mStudent.getLastName();
		studentID = mStudent.getID();
		studentName.setText(name);
		studentCourse.setText(mStudent.getCourse());
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
}
