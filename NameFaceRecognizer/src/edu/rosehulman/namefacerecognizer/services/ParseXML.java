package edu.rosehulman.namefacerecognizer.services;

public class ParseXML {
	
	//responses are always 2 lines, with the 2nd line containing
	//the desired information

	//<?xml version="1.0" encoding="UTF-8"?>
	//<result><error>Invalid username or password</error></result>
	//Or
	//<?xml version="1.0" encoding="UTF-8"?>
	//<result><success>Username and password are valid for davidson</success></result>

	public static boolean validateAuthentication(String username, String[] response) {
		String content = response[1];
		String expected = "<result><success>Username and password are valid for " + username + "</success></result>";
		return content.compareTo(expected) == 0;
	}
}
