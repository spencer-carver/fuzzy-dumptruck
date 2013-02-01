package edu.rosehulman.namefacerecognizer.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.rosehulman.namefacerecognizer.R;
import edu.rosehulman.namefacerecognizer.model.Enrollment;
import edu.rosehulman.namefacerecognizer.services.HttpRequestUtility;
import edu.rosehulman.namefacerecognizer.services.ParseXML;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DownloadActivity extends Activity implements OnClickListener {
	
	private Button reviewButton;
	private Button quizButton;
	private Button downloadButton;
	private Button exitButton;
	
	private static final int REQ_RESET_DEMO = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        //String username = this.getIntent().getStringExtra("username");
        String username = "davidson";
        String requestUrl = "http://angel.rose-hulman.edu/api/default.asp?APIaction=USER_ENROLLMENTS&"+"user="+username;
        String method = "GET";
        Map<String, String> params = new HashMap<String, String>();  //currently pass through the URL, otherwise would pass here
        
        try {   
            String[] response = HttpRequestUtility.sendHttpRequest(requestUrl, method, params);
            
            if (response != null && response.length > 0) {
                Log.d("LDAP","RESPONSE FROM: " + requestUrl);
                for (String line : response) {
                    Log.d("LDAP",line);
                }
                ArrayList<Enrollment> courses = ParseXML.getCourseListing(response);
            }               
        } catch (IOException ex) {
            Log.d("LDAP","ERROR: " + ex.getMessage());
        }
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
		// TODO Auto-generated method stub
		
	}
}