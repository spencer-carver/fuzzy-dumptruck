package edu.rosehulman.namefacerecognizer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.rosehulman.data.DBAdapter;
import edu.rosehulman.nameface.model.Student;
import edu.rosehulman.nameface.model.StudentInfo;

public class MainActivity extends Activity implements OnClickListener {
	
	private Button reviewButton;
	private Button quizButton;
	private Button downloadButton;
	private Button exitButton;
	
	private static final int REQ_RESET_DEMO = 1;
	private static final int REQ_DEFAULT_DB = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reviewButton = (Button) findViewById(R.id.review_button);
        reviewButton.setOnClickListener(this);
        quizButton = (Button) findViewById(R.id.quiz_button);
        quizButton.setOnClickListener(this);
        downloadButton = (Button) findViewById(R.id.download_button);
        downloadButton.setOnClickListener(this);
        exitButton = (Button) findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
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

	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.review_button:
			intent = new Intent(this, ReviewActivity.class);
			startActivity(intent);
			break;
		case R.id.quiz_button:
			intent = new Intent(this, QuizActivity.class);
			startActivity(intent);
			break;
		case R.id.download_button:
			break;
		case R.id.exit_button:
			finish();
			break;
		default:
			//should not get here
		}
		
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
				spencerInfo.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.spencer_angel_pic));
				spencerInfo.setCourse("CSSE490");
				spencer.setStudentInfo(spencerInfo);
				mDBAdapter.addStudent(spencer);
				
				Student marina = new Student();
				StudentInfo marinaInfo = new StudentInfo();
				marinaInfo.setFirstName("Marina");
				marinaInfo.setLastName("Kraeva");
				marinaInfo.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.marina_angel_pic));
				marinaInfo.setCourse("CSSE374");
				marina.setStudentInfo(marinaInfo);
				mDBAdapter.addStudent(marina);
				
				Student dylan = new Student();
				StudentInfo dylanInfo = new StudentInfo();
				dylanInfo.setFirstName("Dylan");
				dylanInfo.setLastName("Kessler");
				dylanInfo.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.dylan_angel_pic));
				dylanInfo.setCourse("CSSE374");
				dylan.setStudentInfo(dylanInfo);
				mDBAdapter.addStudent(dylan);
				
				Student dan = new Student();
				StudentInfo danInfo = new StudentInfo();
				danInfo.setFirstName("Dan");
				danInfo.setLastName("Schepers");
				danInfo.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.dan_angel_pic));
				danInfo.setCourse("CSSE374");
				dan.setStudentInfo(danInfo);
				mDBAdapter.addStudent(dan);
				
				Student frank = new Student();
				StudentInfo frankInfo = new StudentInfo();
				frankInfo.setFirstName("Ziyang");
				frankInfo.setNickName("Frank");
				frankInfo.setLastName("Huang");
				frankInfo.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.frank_angel_pic));
				frankInfo.setCourse("CSSE374");
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
