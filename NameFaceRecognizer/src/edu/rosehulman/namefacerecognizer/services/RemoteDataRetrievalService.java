package edu.rosehulman.namefacerecognizer.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;
import edu.rosehulman.namefacerecognizer.exceptions.InternetConnectivityException;
import edu.rosehulman.namefacerecognizer.model.Enrollment;
import edu.rosehulman.namefacerecognizer.model.Student;
import edu.rosehulman.namefacerecognizer.utils.XmlResponsesParser;

// A fancy way to make a singleton in Java
public enum RemoteDataRetrievalService {

	INSTANCE;
	
	private static final int DEFAULT_BUFFER_SIZE = 1024*1024; // 1MB
	private static final String ANGEL_URL = "http://angel.rose-hulman.edu";
	
	private RemoteDataRetrievalService() {
		
	}
	
	public List<Enrollment> getProfessorCourses(String professorUsername) throws InternetConnectivityException {
		List<Enrollment> result = new ArrayList<Enrollment>();
		String requestUrl = "http://angel.rose-hulman.edu/api/default.asp?APIaction=USER_ENROLLMENTS&"
				+ "user=" + professorUsername;
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
				result = XmlResponsesParser.getCourseListing(response);
				Log.d("LDAP", result.toString());
			}
		} catch (IOException ex) {
			Log.d("LDAP", "ERROR: " + ex.getMessage());
			throw new InternetConnectivityException("Cannot download courses.");
		}

		return result;
	}
	
	public List<Student> getStudentsForSection(String sectionID) throws InternetConnectivityException {
		List<Student> students = new ArrayList<Student>();
		String requestUrl = "http://angel.rose-hulman.edu/api/default.asp?APIaction=SECTION_ROSTER&section=" + sectionID;
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
				students = XmlResponsesParser.getStudentsList(response);
				Log.d("LDAP", students.toString());
			}
		} catch (IOException ex) {
			Log.d("LDAP", "ERROR: " + ex.getMessage());
			throw new InternetConnectivityException("Cannot get students for section " + sectionID);
		}
		
		return students;
	}
	
	public byte[] getBitmapFromURL(String src) throws InternetConnectivityException {
	    try {
	    	String pictureURL = ANGEL_URL + src;
	        URL url = new URL(pictureURL);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        ByteArrayOutputStream output;
	        int filesize = connection.getContentLength();
	        System.out.println("Picture file size: " + filesize);
	        if (filesize != -1) {
	        	output = new ByteArrayOutputStream(filesize);
	        } else {
	        	output = new ByteArrayOutputStream(DEFAULT_BUFFER_SIZE);
	        }
	        byte[] buffer = new byte[512];
	        int bytesRead = input.read(buffer, 0, buffer.length);
	        while(bytesRead != -1 ) {
	        	output.write(buffer, 0, bytesRead);
	        	bytesRead = input.read(buffer);
	        }
	        
	        return output.toByteArray();
	    } catch (IOException e) {
	        e.printStackTrace();
	        throw new InternetConnectivityException("Cannot download image.");
	    }
	}
}
