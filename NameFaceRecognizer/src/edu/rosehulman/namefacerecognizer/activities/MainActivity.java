package edu.rosehulman.namefacerecognizer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import edu.rosehulman.namefacerecognizer.R;
import edu.rosehulman.namefacerecognizer.database.DBAdapter;
import edu.rosehulman.namefacerecognizer.model.Student;
import edu.rosehulman.namefacerecognizer.model.StudentInfo;
import edu.rosehulman.namefacerecognizer.views.MainView;

/**
 * We implement ViewListener because this is how we receive events from the view.
 * The view takes user actions; The controller/activity responds to user actions
 */

public class MainActivity extends Activity implements MainView.ViewListener {
	
	private MainView view;
	
	private static final int REQ_RESET_DEMO = 1;
	private static final int REQ_DEFAULT_DB = 2;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MainView(this, null);
    	view.setViewListener(this);
    	setContentView(view);

    }

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
	
	public void onReviewRequested() {
		Intent intent = new Intent(this, ReviewActivity.class);
		startActivity(intent);
	}
	
	
	public void onQuizRequested() {
		Intent intent = new Intent(this, QuizActivity.class);
		startActivity(intent);
	}
	
	public void onPullStudentsRequested() {
		// TODO: pull student data from database
		Intent intent = new Intent(this, DownloadActivity.class);
		intent.putExtra("username", this.getIntent().getStringExtra("username"));
		startActivity(intent);
	}
	
	public void onExitRequested() {
		finish();
		
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQ_RESET_DEMO:
			if (resultCode == RESULT_OK) {
				DBAdapter mDBAdapter = new DBAdapter(this);
				mDBAdapter.open();
				mDBAdapter.resetDB();
				mDBAdapter.close();
			}
			if (resultCode == RESULT_FIRST_USER) {
				DBAdapter mDBAdapter = new DBAdapter(this);
				mDBAdapter.open();
				Student spencer = new Student();
				StudentInfo spencerInfo = new StudentInfo();
				spencerInfo.setFirstName("Spencer");
				spencerInfo.setLastName("Carver");
//				spencerInfo.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.spencer_angel_pic));
//				spencerInfo.setCourse("CSSE490");
				spencer.setStudentInfo(spencerInfo);
				mDBAdapter.addStudent(spencer);
				
				Student marina = new Student();
				StudentInfo marinaInfo = new StudentInfo();
				marinaInfo.setFirstName("Marina");
				marinaInfo.setLastName("Kraeva");
//				marinaInfo.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.marina_angel_pic));
//				marinaInfo.setCourse("CSSE374");
				marina.setStudentInfo(marinaInfo);
				mDBAdapter.addStudent(marina);
				
				Student dylan = new Student();
				StudentInfo dylanInfo = new StudentInfo();
				dylanInfo.setFirstName("Dylan");
				dylanInfo.setLastName("Kessler");
//				dylanInfo.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.dylan_angel_pic));
//				dylanInfo.setCourse("CSSE374");
				dylan.setStudentInfo(dylanInfo);
				mDBAdapter.addStudent(dylan);
				
				Student dan = new Student();
				StudentInfo danInfo = new StudentInfo();
				danInfo.setFirstName("Dan");
				danInfo.setLastName("Schepers");
//				danInfo.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.dan_angel_pic));
//				danInfo.setCourse("CSSE374");
				dan.setStudentInfo(danInfo);
				mDBAdapter.addStudent(dan);
				
				Student frank = new Student();
				StudentInfo frankInfo = new StudentInfo();
				frankInfo.setFirstName("Ziyang");
				frankInfo.setNickName("Frank");
				frankInfo.setLastName("Huang");
//				frankInfo.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.frank_angel_pic));
//				frankInfo.setCourse("CSSE374");
				frank.setStudentInfo(frankInfo);
				mDBAdapter.addStudent(frank);
				
				mDBAdapter.close();
			}
			break;
		default:
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
}
