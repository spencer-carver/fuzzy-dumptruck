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
	private AuthenticationService() {
		// TODO: Set-up connection?
	}
	
	public boolean authenticate(String username, String password) {
		// TODO: Authenticate with real service
		if(username.equals("admin") || ANGELAuthentication(username, password)) { // cannot access R.string outside a Context, so hardcoding this for now
			return true;
		}
		
		return false;
	}
	
	private boolean ANGELAuthentication(String username, String password) {
		String requestUrl = "http://angel.rose-hulman.edu";
        String method = "GET";
        Map<String, String> params = new HashMap<String, String>();
        
        try {
            String[] response = HttpRequestUtility.sendHttpRequest(requestUrl, method, params);
            if (response != null && response.length > 0) {
                Log.d("LDAP","RESPONSE FROM: " + requestUrl);
                for (String line : response) {
                    Log.d("LDAP",line);
                }
            }
            
            requestUrl = "http://angel.rose-hulman.edu/api/default.asp?APIaction=VALIDATE_ACCOUNT&user=carvers&password=chris_is_a_jerk";
            
            response = HttpRequestUtility.sendHttpRequest(requestUrl, method, params);
            
            if (response != null && response.length > 0) {
                Log.d("LDAP","RESPONSE FROM: " + requestUrl);
                for (String line : response) {
                    Log.d("LDAP",line);
                }
            }               
        } catch (IOException ex) {
            Log.d("LDAP","ERROR: " + ex.getMessage());
        }
		return false;
	}

}
