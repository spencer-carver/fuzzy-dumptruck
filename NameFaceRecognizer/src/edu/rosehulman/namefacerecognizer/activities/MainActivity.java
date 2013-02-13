package edu.rosehulman.namefacerecognizer.activities;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import edu.rosehulman.namefacerecognizer.R;
import edu.rosehulman.namefacerecognizer.model.Enrollment;
import edu.rosehulman.namefacerecognizer.model.Student;
import edu.rosehulman.namefacerecognizer.services.PersistenceService;
import edu.rosehulman.namefacerecognizer.views.MainView;
import edu.rosehulman.namefacerecognizer.views.SectionsDialog;

/**
 * We implement ViewListener because this is how we receive events from the view.
 * The view takes user actions; The controller/activity responds to user actions
 */

public class MainActivity extends Activity implements MainView.ViewListener {
		
	public static final String SECTION_IDS = "sectionIDs";
	
	private static final int REQ_RESET_DEMO = 1;
	private static final int REQ_DEFAULT_DB = 2;
	
	private MainView view;
	
	private String username;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MainView(this, null);
    	view.setViewListener(this);
    	setContentView(view);
    	this.username = this.getIntent().getStringExtra("username");
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
		List<Enrollment> sectionsList = PersistenceService.getInstance(getApplicationContext()).getSectionsForProfessor(username);

		if (sectionsList == null || sectionsList.isEmpty()) {
			Toast.makeText(this, "No sections available. Please download sections before reviewing.",
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		showSectionsChooserDialog(sectionsList, ReviewActivity.class);
	}
	
	public void onQuizRequested() {
		List<Enrollment> sectionsList = PersistenceService.getInstance(getApplicationContext()).getSectionsForProfessor(username);
		if (sectionsList == null || sectionsList.isEmpty()) {
			Toast.makeText(this, "No sections available. Please download sections before quizzing.",
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		showSectionsChooserDialog(sectionsList, QuizActivity.class);
	}

	private void startNextActivity(ArrayList<String> sectionsList, Class<?> activityType) {
		Intent intent = new Intent(this, activityType);
		Bundle b = new Bundle();
		b.putStringArrayList(SECTION_IDS, sectionsList);
		intent.putExtras(b);
		startActivity(intent);
	}
	
	public void onPullStudentsRequested() {
		Intent intent = new Intent(this, DownloadActivity.class);
		intent.putExtra("username", username);
		startActivity(intent);
	}
	
	public void onExitRequested() {
		finish();
		
	}
	
	private void showSectionsChooserDialog(List<Enrollment> enrollments, final Class<?> activityType) {
		SectionsDialog dialog = new SectionsDialog(this);
		dialog.setSections(enrollments);
		dialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				SectionsDialog clickedDialog = (SectionsDialog)dialog; // should be safe since the click is made inside our custom dialog
				ArrayList<String> sectionIDs = new ArrayList<String>(clickedDialog.getSelectedSections());
				dialog.dismiss();
				startNextActivity(sectionIDs, activityType);
			}
		});
		dialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		dialog.show();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQ_RESET_DEMO:
			if (resultCode == RESULT_OK) {
				PersistenceService.getInstance(getApplicationContext()).clearAllPersistedData();
			}
			if (resultCode == RESULT_FIRST_USER) {
				List<Student> students = new ArrayList<Student>();
				Map<String, byte[]> studentsPictures = new HashMap<String, byte[]>();
				
				Student spencer = new Student();
				spencer.setFirstName("Spencer");
				spencer.setLastName("Carver");
				spencer.setUsername("carvers");
//				spencerInfo.setCourse("CSSE490");
				students.add(spencer);
				Bitmap spencerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.spencer_angel_pic);
				ByteArrayOutputStream spencerStream = new ByteArrayOutputStream();
			    spencerBitmap.compress(Bitmap.CompressFormat.JPEG, 100, spencerStream);
			    byte[] spencerBitmapData = spencerStream.toByteArray();
				studentsPictures.put(spencer.getUsername(), spencerBitmapData );
				
				Student marina = new Student();
				marina.setFirstName("Marina");
				marina.setLastName("Kraeva");
				marina.setUsername("kraevam");
//				marinaInfo.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.marina_angel_pic));
//				marinaInfo.setCourse("CSSE374");
				students.add(marina);
				Bitmap marinaBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.spencer_angel_pic);
				ByteArrayOutputStream marinaStream = new ByteArrayOutputStream();
			    marinaBitmap.compress(Bitmap.CompressFormat.JPEG, 100, marinaStream);
			    byte[] marinaBitmapData = marinaStream.toByteArray();
				studentsPictures.put(spencer.getUsername(), marinaBitmapData );
				
				Student dylan = new Student();
				dylan.setFirstName("Dylan");
				dylan.setLastName("Kessler");
				dylan.setUsername("kesslerd");
//				dylanInfo.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.dylan_angel_pic));
//				dylanInfo.setCourse("CSSE374");
				students.add(dylan);
				Bitmap dylanBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.spencer_angel_pic);
				ByteArrayOutputStream dylanStream = new ByteArrayOutputStream();
			    dylanBitmap.compress(Bitmap.CompressFormat.JPEG, 100, dylanStream);
			    byte[] dylanBitmapData = dylanStream.toByteArray();
				studentsPictures.put(spencer.getUsername(), dylanBitmapData );
				
				Student dan = new Student();
				dan.setFirstName("Dan");
				dan.setLastName("Schepers");
				dan.setUsername("schepers");
//				danInfo.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.dan_angel_pic));
//				danInfo.setCourse("CSSE374");
				students.add(dan);
				Bitmap danBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.spencer_angel_pic);
				ByteArrayOutputStream danStream = new ByteArrayOutputStream();
			    danBitmap.compress(Bitmap.CompressFormat.JPEG, 100, danStream);
			    byte[] danBitmapData = danStream.toByteArray();
				studentsPictures.put(spencer.getUsername(), danBitmapData );

				Student frank = new Student();
				frank.setFirstName("Ziyang");
				frank.setNickName("Frank");
				frank.setLastName("Huang");
				frank.setUsername("huangz");
//				frankInfo.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.frank_angel_pic));
//				frankInfo.setCourse("CSSE374");
				students.add(frank);
				Bitmap frankBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.spencer_angel_pic);
				ByteArrayOutputStream frankStream = new ByteArrayOutputStream();
			    frankBitmap.compress(Bitmap.CompressFormat.JPEG, 100, frankStream);
			    byte[] frankBitmapData = frankStream.toByteArray();
				studentsPictures.put(spencer.getUsername(), frankBitmapData );

				PersistenceService.getInstance(getApplicationContext()).persistStudentInfo(students);
				PersistenceService.getInstance(getApplicationContext()).persistStudentPictures(studentsPictures);
				
			}
			break;
		default:
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
}
