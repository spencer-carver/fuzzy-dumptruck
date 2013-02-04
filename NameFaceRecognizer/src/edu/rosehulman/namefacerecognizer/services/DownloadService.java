package edu.rosehulman.namefacerecognizer.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import edu.rosehulman.namefacerecognizer.activities.DownloadActivity;
import edu.rosehulman.namefacerecognizer.database.DBAdapter;
import edu.rosehulman.namefacerecognizer.model.Enrollment;
import edu.rosehulman.namefacerecognizer.model.Student;
import edu.rosehulman.namefacerecognizer.utils.XmlResponsesParser;

public class DownloadService {
	
	private List<Student> mStudents;

	public boolean download(Enrollment e, DBAdapter dbA) {
		String requestUrl = "http://angel.rose-hulman.edu/api/default.asp?APIaction=SECTION_ROSTER&section=" + e.getSectionID();
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
				mStudents = XmlResponsesParser.getStudentsList(response);
				for (Student s : mStudents) {
					s.setPicture(getBitmapFromURL(s.getImagePath()));
					dbA.addStudent(s);
				}
				Log.d("LDAP", mStudents.toString());
			}
		} catch (IOException ex) {
			Log.d("LDAP", "ERROR: " + ex.getMessage());
		}
		return false;		
	}
	
	public static Bitmap getBitmapFromURL(String src) {
	    try {
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}
