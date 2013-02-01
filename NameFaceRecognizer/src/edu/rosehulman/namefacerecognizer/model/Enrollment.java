package edu.rosehulman.namefacerecognizer.model;

public class Enrollment {

	private String SectionID;
	private String SectionTitle;
	private String SectionCategory;
	private boolean selected;

	public String getSectionID() {
		return SectionID;
	}

	public void setSectionID(String sectionID) {
		SectionID = sectionID;
	}

	public String getSectionTitle() {
		return SectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		SectionTitle = sectionTitle;
	}

	public String getSectionCategory() {
		return SectionCategory;
	}

	public void setSectionCategory(String sectionCategory) {
		SectionCategory = sectionCategory;
	}
	
	public void parseEnrollment(String xml) {
		//looks like: <enrollment SECTION_ID="ALL-ALL-ANGEL-ANGEL-1" SECTION_TITLE="Pathway to ANGEL" SECTION_CATEGORY="Training"/>
		String[] split = xml.split("\"");
		SectionCategory = split[1];
		SectionTitle = split[3];
		SectionID = split[5];
	}
	
	public String toString() {
		return "" + SectionTitle;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
