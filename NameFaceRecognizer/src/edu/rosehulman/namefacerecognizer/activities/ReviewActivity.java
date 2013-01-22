package edu.rosehulman.namefacerecognizer.activities;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import edu.rosehulman.namefacerecognizer.R;
import edu.rosehulman.namefacerecognizer.database.DBAdapter;
import edu.rosehulman.namefacerecognizer.model.Review;
import edu.rosehulman.namefacerecognizer.model.Section;
import edu.rosehulman.namefacerecognizer.model.Student;
import edu.rosehulman.namefacerecognizer.views.EditView;
import edu.rosehulman.namefacerecognizer.views.ReviewView;

public class ReviewActivity extends Activity implements EditView.EditViewListener {

//	private String KEY_STUDENT_ID = "id";
//	private int REQ_GOTO_EDIT = 2;
	
	private ReviewView reviewView;
	private EditView editView;
	
	private Review review;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		reviewView = new ReviewView(this, null);

		// TODO: This should be in a separate method, getting info about the sections and students from the DB
		DBAdapter dbAdapter = new DBAdapter(this);
		dbAdapter.open();
		review = new Review();
		List<Student> students = dbAdapter.getAllStudents();
		dbAdapter.close();
		Section section = new Section("CSSE374-01", "201302", "CID", "boutell");
		section.addStudents(students);
		review.addSection(section);
		
		reviewView.setReviewData(review);
		setContentView(reviewView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_review, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == R.id.review_menu_edit) {
			//Toast.makeText(this, "Clicked Edit", Toast.LENGTH_LONG).show();
			//TODO fix later if need be
/*			
			 This is replaced by just switching the view
			 because it is easier to pass the Student object that way
			 Smarter ways to handle this will be appreciated...
			 Fragments could work, but they are since Android 3.0, which is above our lowest target version
			 -- kraevam
 */
//			Intent intent = new Intent(this, EditActivity.class);
//			intent.putExtra(KEY_STUDENT_ID, studentID);
//			startActivityForResult(intent, REQ_GOTO_EDIT);
			switchToEditView(reviewView.getSelectedStudent());
		}
		return false;
	}

	public void onCancelRequested() {
		switchToReviewView(null);
	}

	public void onChangeRequested(Student student, String newNickname, String newNotes) {
		// TODO: update student in DB
		student.setNickName(newNickname);
		student.setNote(newNotes);
		switchToReviewView(student);
	}

	private void switchToEditView(Student student) {
		editView = new EditView(this, null);
		editView.setListener(this);
		editView.setStudent(student);
		this.setContentView(editView);
	}

	private void switchToReviewView(Student selectedStudent) {
		setContentView(reviewView);
		if (selectedStudent != null) {			
			reviewView.setSelectedStudent(selectedStudent);
		}
		editView = null; // so that it doesn't stay in memory
	}
}
