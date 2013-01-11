package edu.rosehulman.namefacerecognizer;

import java.util.List;

import edu.rosehulman.data.DBAdapter;
import edu.rosehulman.data.StudentInfo;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReviewActivity extends Activity implements OnClickListener {

	private ImageView mainImage;
	private TextView studentName;
	private TextView studentCourse;
	private LinearLayout filmstrip;
	private List<StudentInfo> mStudents;
	private DBAdapter mDBAdapter;
	
	
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
		for (StudentInfo student : mStudents) {
			ImageView sView = new ImageView(this);
			sView.setId(student.getID()); // TODO get working
			sView.setLayoutParams(new LinearLayout.LayoutParams(filmstrip_dimension, filmstrip_dimension));
			sView.setImageBitmap(student.getPicture());
			sView.setOnClickListener(this);
			filmstrip.addView(sView);
		}
		StudentInfo defaultStudent = mStudents.get(0);
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

	public void onClick(View v) {
		StudentInfo mStudent = new StudentInfo();
		for (StudentInfo student : mStudents) {
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
		studentName.setText(name);
		studentCourse.setText(mStudent.getCourse());
	}
}
