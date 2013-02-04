package edu.rosehulman.namefacerecognizer.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import edu.rosehulman.namefacerecognizer.R;
import edu.rosehulman.namefacerecognizer.database.DBAdapter;
import edu.rosehulman.namefacerecognizer.model.Enrollment;
import edu.rosehulman.namefacerecognizer.services.DownloadService;
import edu.rosehulman.namefacerecognizer.services.EnrollmentAdapter;
import edu.rosehulman.namefacerecognizer.services.HttpRequestUtility;
import edu.rosehulman.namefacerecognizer.utils.XmlResponsesParser;

public class DownloadActivity extends Activity{

	private ListView mCourseListView;
	private List<Enrollment> mCourses;
	private EnrollmentAdapter mCourseAdapter;
	private int mNumSelected;
	private List<Enrollment> mSelectedCourses = new ArrayList<Enrollment>();
	private Button mDownloadButton;
	private Button mBackButton;
	private DownloadService mDownloadService = new DownloadService();

	private static final int REQ_RESET_DEMO = 1;
	
	public void incrementNumSelected(int pos) {
		if (mNumSelected == 0) {
			mDownloadButton.setClickable(true);
			mDownloadButton.setTextColor(getResources().getColor(R.color.black));
		}
		mNumSelected++;
		mDownloadButton.setText("Download (" + mNumSelected + ")");
		mSelectedCourses.add(mCourses.get(pos));
	}
	
	public void decrementNumSelected(int pos) {
		mNumSelected--;
		mDownloadButton.setText("Download (" + mNumSelected + ")");
		mSelectedCourses.remove(mCourses.get(pos));
		if (mNumSelected == 0) {
			mDownloadButton.setClickable(false);
			mDownloadButton.setTextColor(getResources().getColor(R.color.gray));
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		String username = this.getIntent().getStringExtra("username");
		String requestUrl = "http://angel.rose-hulman.edu/api/default.asp?APIaction=USER_ENROLLMENTS&"
				+ "user=" + username;
		String method = "GET";
		// currently pass through the URL, otherwise would pass here
		Map<String, String> params = new HashMap<String, String>();

		try {
			String[] response = HttpRequestUtility.sendHttpRequest(requestUrl,
					method, params);

			if (response != null && response.length > 0) {
				Log.d("LDAP", "RESPONSE FROM: " + requestUrl);
				for (String line : response) {
					Log.d("LDAP", line);
				}
				mCourses = XmlResponsesParser.getCourseListing(response);
				Log.d("LDAP", mCourses.toString());
			}
		} catch (IOException ex) {
			Log.d("LDAP", "ERROR: " + ex.getMessage());
		}
		final DBAdapter dbA = new DBAdapter(this);
		mDownloadButton = (Button) findViewById(R.id.download_button);
		mDownloadButton.setClickable(false);
		mDownloadButton.setTextColor(getResources().getColor(R.color.gray));
		mDownloadButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dbA.open();
				for (Enrollment e : mSelectedCourses) {
					mDownloadService.download(e, dbA);
				}
				dbA.close();
				finish();
			}
			
		});
		mBackButton = (Button) findViewById(R.id.back_button);
		mBackButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				finish();
			}
			
		});
		mCourseListView = (ListView) findViewById(R.id.display_courses_list);
		mCourseAdapter = new EnrollmentAdapter(this,
				R.layout.course_listview, mCourses);
		mCourseListView.setAdapter(mCourseAdapter);
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
}