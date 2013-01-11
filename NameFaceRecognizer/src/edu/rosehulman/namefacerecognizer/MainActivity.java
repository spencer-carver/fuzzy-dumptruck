package edu.rosehulman.namefacerecognizer;

import edu.rosehulman.data.DBAdapter;
import edu.rosehulman.data.StudentInfo;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
				StudentInfo spencer = new StudentInfo();
				spencer.setFirstName("Spencer");
				spencer.setLastName("Carver");
				spencer.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.spencer_angel_pic));
				spencer.setCourse("CSSE490");
				mDBAdapter.addStudent(spencer);
				StudentInfo marina = new StudentInfo();
				marina.setFirstName("Marina");
				marina.setLastName("Kraeva");
				marina.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.marina_angel_pic));
				marina.setCourse("CSSE374");
				mDBAdapter.addStudent(marina);
				StudentInfo dylan = new StudentInfo();
				dylan.setFirstName("Dylan");
				dylan.setLastName("Kessler");
				dylan.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.dylan_angel_pic));
				dylan.setCourse("CSSE374");
				mDBAdapter.addStudent(dylan);
				StudentInfo dan = new StudentInfo();
				dan.setFirstName("Dan");
				dan.setLastName("Schepers");
				dan.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.dan_angel_pic));
				dan.setCourse("CSSE374");
				mDBAdapter.addStudent(dan);
				StudentInfo frank = new StudentInfo();
				frank.setFirstName("Ziyang");
				frank.setNickName("Frank");
				frank.setLastName("Huang");
				frank.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.frank_angel_pic));
				frank.setCourse("CSSE374");
				mDBAdapter.addStudent(frank);
				mDBAdapter.close();
			}
			break;
		default:
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
}
