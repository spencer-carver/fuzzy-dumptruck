package edu.rosehulman.namefacerecognizer.utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.util.Log;
import edu.rosehulman.namefacerecognizer.model.Enrollment;
import edu.rosehulman.namefacerecognizer.model.Student;

public class XmlResponsesParser {

	private final static String SUCCESS_NODE_NAME = "success";
	private final static String ENROLLMENTS_NODE_NAME = "enrollments";
	private final static String ENROLLMENT_NODE_NAME = "enrollment";
	private final static String SUCCESS_TEXT = "Username and password are valid for ";
	
	public static boolean validateAuthentication(String username, String[] response) {
		String xmlResponse = getXmlFromResponse(response);
		
		boolean authenticated = false;
		Document document;
		try {
			document = getXMLDocumentFromString(xmlResponse);
			Node rootElement = document.getDocumentElement();
			NodeList nodes = rootElement.getChildNodes();
			for (int i=0; i<nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Element.ELEMENT_NODE && node.getNodeName().equals(SUCCESS_NODE_NAME)) {
					if (node.getTextContent().equals(SUCCESS_TEXT + username)) {						
						authenticated = true;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return authenticated;
	}

	public static List<Enrollment> getCourseListing(String[] response) {
		String xmlResponse = getXmlFromResponse(response);
		List<Enrollment> courses = new ArrayList<Enrollment>();
		Document document = null;
		try {
			document = getXMLDocumentFromString(xmlResponse);
			/*
			 * <result>
			 * <success/>
			 * <enrollments>
			 * <enrollment SECTION_ID="201120-M-RHIT-JP-112-01-2284" SECTION_TITLE="JP112-01 Japanese Language & Culture II" SECTION_CATEGORY="201120 - Winter Quarter - 2010-11"/>
			 * <enrollment SECTION_ID="ALL-ALL-ALL-ANGEL-Test2" SECTION_TITLE="ANGEL Learning Test Course II" SECTION_CATEGORY="Training"/>
			 * <enrollment SECTION_ID="ALL-ALL-ANGEL-ANGEL-1" SECTION_TITLE="Pathway to ANGEL" SECTION_CATEGORY="Training"/>
			 * <enrollment SECTION_ID="FY09-ALL-ANGEL-Pathway_to_ANGEL_-_Fall_2008-F2008" SECTION_TITLE="Pathway to ANGEL - Fall 2008" SECTION_CATEGORY="Training"/>
			 * <enrollment SECTION_ID="MRG-080902-103704-7da7a9c1-e859-47c1-83f0-bd9af4985db1" SECTION_TITLE="Pathway to ANGEL - Merged" SECTION_CATEGORY="Training"/>
			 * </enrollments>
			 * </result>
			 */
			Node rootNode = document.getDocumentElement();
			NodeList childNodes = rootNode.getChildNodes();
			for (int i=0; i<childNodes.getLength(); i++) {
				Node child = childNodes.item(i);
				if(child.getNodeType() == Element.ELEMENT_NODE && child.getNodeName().equals(ENROLLMENTS_NODE_NAME)) {
					NodeList sectionNodes = child.getChildNodes();
					for (int j=0; j<sectionNodes.getLength(); j++) {
						Node sectionNode = sectionNodes.item(j);
						if(sectionNode.getNodeType() == Element.ELEMENT_NODE && sectionNode.getNodeName().equals(ENROLLMENT_NODE_NAME)) {
							String sectionId = ((Element)sectionNode).getAttribute("SECTION_ID");
							String sectionTitle = ((Element)sectionNode).getAttribute("SECTION_TITLE");
							String sectionCategory = ((Element)sectionNode).getAttribute("SECTION_CATEGORY");
							// TODO: Are these the actual fields that go in the constructor?
							// Section section = new Section(sectionId,sectionCategory, sectionTitle);
							Enrollment course = new Enrollment();
							course.setSectionID(sectionId);
							course.setSectionCategory(sectionCategory);
							course.setSectionTitle(sectionTitle);
							courses.add(course);
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return courses;
	}
	
	public static List<Student> getStudentsList(String[] response) {
		String xmlResponse = getXmlFromResponse(response);
		List<Student> students = new ArrayList<Student>();
		Document document = null;
		/*
		 * <result>
		 * <success/>
		 * <roster>
		 *  <rec PHOTO="/IDphotos/NFCCTRXZ.jpg" LOGINNAME="connolrm" FIRST_NAME="Raymond" LAST_NAME="Connolly"/>
		 *   <rec PHOTO="/IDphotos/MGRJLXYS.jpg" LOGINNAME="deckerbm" FIRST_NAME="Benjamin" LAST_NAME="Decker"/> 
		 *   <rec PHOTO="/IDphotos/YXRLVBMD.jpg" LOGINNAME="diniusrh" FIRST_NAME="Roger" LAST_NAME="Dinius"/> 
		 *   <rec PHOTO="/IDphotos/FYWNLFLW.jpg" LOGINNAME="fullerja" FIRST_NAME="Justin" LAST_NAME="Fuller"/>
		 *   <rec PHOTO="/IDphotos/WMSCYXGS.jpg" LOGINNAME="greendc" FIRST_NAME="Dawson" LAST_NAME="Green"/> 
		 *   <rec PHOTO="/IDphotos/HLJWSBJR.jpg" LOGINNAME="gutsuerr" FIRST_NAME="Rex" LAST_NAME="Gutsue"/> 
		 *   <rec PHOTO="/IDphotos/GFTFLNGJ.jpg" LOGINNAME="hoodtj" FIRST_NAME="Trevor" LAST_NAME="Hood"/> 
		 *   <rec PHOTO="/IDphotos/BPXVWBQC.jpg" LOGINNAME="kinneyrm" FIRST_NAME="Ryan" LAST_NAME="Kinney"/> 
		 *   <rec PHOTO="/IDphotos/GXZFPQVT.jpg" LOGINNAME="newcomkp" FIRST_NAME="Kevin" LAST_NAME="Newcomer"/> 
		 *   <rec PHOTO="/IDphotos/NKTDKCCS.jpg" LOGINNAME="ziebelje" FIRST_NAME="Jonathan" LAST_NAME="Ziebell"/> 
		 *   </roster>
		 *   </result>


		 */
		try {
			document = getXMLDocumentFromString(xmlResponse);
			
			Node rootNode = document.getDocumentElement();
			NodeList childNodes = rootNode.getChildNodes();
			for (int i=0; i<childNodes.getLength(); i++) {
				Node child = childNodes.item(i);
				if(child.getNodeType() == Element.ELEMENT_NODE && child.getNodeName().equals("roster")) {
					NodeList rosterNodes = child.getChildNodes();
					for (int j=0; j<rosterNodes.getLength(); j++) {
						Node recordNode = rosterNodes.item(j);
						if(recordNode.getNodeType() == Element.ELEMENT_NODE && recordNode.getNodeName().equals("rec")) {
							String firstName = ((Element)recordNode).getAttribute("FIRST_NAME");
							String lastName = ((Element)recordNode).getAttribute("LAST_NAME");
							String username = ((Element)recordNode).getAttribute("LOGINNAME");
							String photoPath = ((Element)recordNode).getAttribute("PHOTO");
							Log.d("LDAP", firstName + ", " + lastName + ", " + photoPath);
							Student student = new Student();
							student.setFirstName(firstName);
							student.setLastName(lastName);
							student.setUsername(username);
							student.setImagePath(photoPath);
							students.add(student);
						}
					}
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return students;
	}
	
	private static Document getXMLDocumentFromString(String xmlString) throws Exception {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xmlString.toString())));
			return document;
		} catch (Exception ex) {
			// something went wrong in the response
			throw ex;
		}
	}
	
	private static String getXmlFromResponse(String[] response) {
		// first line is not part of the xml
		StringBuilder xmlBuilder = new StringBuilder();
		for(int i=1; i<response.length; i++) {
			xmlBuilder.append(response[i]);
		}
		
		return xmlBuilder.toString();
	}
}
