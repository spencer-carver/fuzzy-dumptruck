package edu.rosehulman.namefacerecognizer.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;


public class AuthenticationService {
	

	/**
	 * A Singleton class that handles connections to the authentication service
	 * At first will connect to ANGEL, might use Banner Web later on
	 * @ author kraevam
	 */
	private static AuthenticationService instance;
	
	public static AuthenticationService getInstance() {
		if (instance == null) {
			instance = new AuthenticationService();
		}
		return instance;
	}
	
	public boolean authenticate(String username, String password) {
		return ANGELAuthentication(username, password);
	}
	
	private boolean ANGELAuthentication(String username, String password) {
		boolean validated = false;
		String requestUrl = "http://angel.rose-hulman.edu/api/default.asp?APIaction=VALIDATE_ACCOUNT&"+"user="+username+"&password="+password;
        String method = "GET";
        Map<String, String> params = new HashMap<String, String>();  //currently pass through the URL, otherwise would pass here
        
        try {   
            String[] response = HttpRequestUtility.sendHttpRequest(requestUrl, method, params);
            
            if (response != null && response.length > 0) {
                Log.d("LDAP","RESPONSE FROM: " + requestUrl);
                for (String line : response) {
                    Log.d("LDAP",line);
                }
                validated = ParseXML.validateAuthentication(username, response);
            }               
        } catch (IOException ex) {
            Log.d("LDAP","ERROR: " + ex.getMessage());
        }
		return validated;
	}

}
