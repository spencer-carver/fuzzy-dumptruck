package edu.rosehulman.namefacerecognizer.model;

public class Enrollment {

	private String sectionID;
	private String sectionTitle;
	private String sectionCategory;
	private boolean selected;

	public String getSectionID() {
		return this.sectionID;
	}

	public void setSectionID(String sectionID) {
		this.sectionID = sectionID;
	}

	public String getSectionTitle() {
		return this.sectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	public String getSectionCategory() {
		return this.sectionCategory;
	}

	public void setSectionCategory(String sectionCategory) {
		this.sectionCategory = sectionCategory;
	}
	
	public String toString() {
		return "" + sectionTitle;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
