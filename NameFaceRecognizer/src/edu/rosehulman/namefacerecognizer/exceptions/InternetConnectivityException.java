package edu.rosehulman.namefacerecognizer.exceptions;

public class InternetConnectivityException extends Exception {

	private static final long serialVersionUID = 1L;

	public InternetConnectivityException(String message) {
		super("Problems connecting to server: " + message);
	}

}
