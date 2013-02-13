package edu.rosehulman.namefacerecognizer.activities;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import edu.rosehulman.namefacerecognizer.R;
import edu.rosehulman.namefacerecognizer.model.Enrollment;
import edu.rosehulman.namefacerecognizer.model.Student;
import edu.rosehulman.namefacerecognizer.services.PersistenceService;
import edu.rosehulman.namefacerecognizer.services.RemoteDataRetrievalService;
import edu.rosehulman.namefacerecognizer.views.DownloadView;

public class DownloadActivity extends Activity implements DownloadView.DownloadViewListener {

	private static final int REQ_RESET_DEMO = 1;

	private List<Enrollment> mCourses;
	private String username;
	private DownloadView view;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.username = this.getIntent().getStringExtra("username");
		this.view = new DownloadView(this, null);
		this.view.setListener(this);
		setContentView(this.view);
		this.mCourses = retrieveSections();
		this.view.setCourses(this.mCourses);
	}

	private List<Enrollment> retrieveSections() {
		List<Enrollment> allSections =  RemoteDataRetrievalService.INSTANCE.getProfessorCourses(username);
		PersistenceService.getInstance(this.getApplicationContext()).markPersistedSections(allSections);
		return allSections;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == R.id.menu_settings) {
			Intent intent = new Intent(this, MainActivityPreferences.class);
			startActivityForResult(intent, REQ_RESET_DEMO);
		}
		return false;
	}

	public void onDownloadButtonClicked() {
		List<Enrollment> coursesToDownload = view.getCoursesForDownload();
		for (Enrollment e : coursesToDownload) {
			List<Student> studentsInCourse = RemoteDataRetrievalService.INSTANCE.getStudentsForSection(e.getSectionID());
			PersistenceService.getInstance(this.getApplicationContext()).persistSection(e, username);
			PersistenceService.getInstance(this.getApplicationContext()).persistStudentInfo(studentsInCourse);
			
			for(Student student : studentsInCourse) {
				PersistenceService.getInstance(getApplicationContext()).addStudentToSection(e, student);
				byte[] pictureData = RemoteDataRetrievalService.INSTANCE.getBitmapFromURL(student.getImagePath());
				PersistenceService.getInstance(this.getApplicationContext()).persistStudentPicture(student.getUsername(), pictureData);
			}
		}
		finish();
	
	}

	public void onBackButtonClicked() {
		finish();
	}
}