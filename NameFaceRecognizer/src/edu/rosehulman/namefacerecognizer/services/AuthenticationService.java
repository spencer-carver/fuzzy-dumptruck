package edu.rosehulman.namefacerecognizer.services;


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
		if(username.equals("admin")) { // cannot access R.string outside a Context, so hardcoding this for now
			return true;
		}
		
		return false;
	}

}
