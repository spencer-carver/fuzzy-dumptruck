package edu.rosehulman.namefacerecognizer.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import edu.rosehulman.namefacerecognizer.R;
import edu.rosehulman.namefacerecognizer.model.Review;
import edu.rosehulman.namefacerecognizer.model.Student;
import edu.rosehulman.namefacerecognizer.services.PersistenceService;
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
		List<Student> students = getStudentsForReview();
		review = new Review();
		students=sortByEval(students);
		review.addStudents(students);
		reviewView.setReviewData(review);
		setContentView(reviewView);
	}

	
	
	private List<Student> getStudentsForReview() {
		List<String> sectionIDs = this.getIntent().getStringArrayListExtra(MainActivity.SECTION_IDS);
		Set<Student> allStudents = new HashSet<Student>();
		for (String sectionID : sectionIDs) {
			List<Student> sectionStudents = PersistenceService.getInstance(getApplicationContext()).getStudentsForSection(sectionID);
			allStudents.addAll(sectionStudents);
		}
		List<Student> students = new ArrayList<Student>(allStudents);
		return students;
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
		PersistenceService.getInstance(getApplicationContext()).updateStudentInfo(student);
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

/**
 *  Sorts the students by their e Value
 *  places emphasis on students that the prof doesn't know/hasn't seen 
 *
 * @param students
 * @return
 */
private List<Student> sortByEval(List<Student> students) {
	studentComparator comparator=new studentComparator();
	for (int i=0; i <students.size();i++){
		System.out.println((students.get(i).getEValue()));
	}
	Collections.sort(students,comparator);
	System.out.println("after sort");
	for (int i=0; i <students.size();i++){
		System.out.println((students.get(i).getEValue()));
	}
	return students;
}

private class studentComparator implements Comparator<Student>{
	public int compare(Student s1, Student s2){	
		double eval1=s1.getEValue();
		double eval2=s2.getEValue();
		 if(eval1>eval2)
              return +1;
          else if(eval1<eval2)
              return -1;
          else
              return 0;
	}	
	
}}