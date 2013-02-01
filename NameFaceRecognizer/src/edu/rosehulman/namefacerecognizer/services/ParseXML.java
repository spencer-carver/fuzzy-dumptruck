package edu.rosehulman.namefacerecognizer.services;

import java.util.ArrayList;

import android.util.Log;

import edu.rosehulman.namefacerecognizer.model.Enrollment;

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
	
	//<?xml version="1.0" encoding="UTF-8"?>
	/* <result><success/><enrollments>
	 * <enrollment SECTION_ID="201120-M-RHIT-JP-112-01-2284" SECTION_TITLE="JP112-01 Japanese Language & Culture II" 
	 * SECTION_CATEGORY="201120 - Winter Quarter - 2010-11"/>
	 * <enrollment SECTION_ID="ALL-ALL-ALL-ANGEL-Test2" SECTION_TITLE="ANGEL Learning Test Course II" SECTION_CATEGORY="Training"/>
	 * <enrollment SECTION_ID="ALL-ALL-ANGEL-ANGEL-1" SECTION_TITLE="Pathway to ANGEL" SECTION_CATEGORY="Training"/>
	 * <enrollment SECTION_ID="FY09-ALL-ANGEL-Pathway_to_ANGEL_-_Fall_2008-F2008" SECTION_TITLE="Pathway to ANGEL - Fall 2008" SECTION_CATEGORY="Training"/>
	 * <enrollment SECTION_ID="MRG-080902-103704-7da7a9c1-e859-47c1-83f0-bd9af4985db1" SECTION_TITLE="Pathway to ANGEL - Merged" SECTION_CATEGORY="Training"/>
	 * </enrollments></result>
	 */

	public static ArrayList<Enrollment> getCourseListing(String[] response) {
		// for this class, there is an assumption currently being made that is the pattern '/><' will only appear in xml,and not in any of the fields
		ArrayList<Enrollment> courses = new ArrayList<Enrollment>();
		for (int i = response.length - 2; i > 1; i--) {
			Enrollment course = new Enrollment();
			course.parseEnrollment(response[i]);
			courses.add(course);
		}
		return courses;
	}
}
