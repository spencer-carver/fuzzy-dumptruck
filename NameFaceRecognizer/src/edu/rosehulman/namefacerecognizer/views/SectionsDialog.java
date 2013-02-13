package edu.rosehulman.namefacerecognizer.views;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import edu.rosehulman.namefacerecognizer.model.Enrollment;

public class SectionsDialog extends AlertDialog {

	private SectionsListView sectionsListView;

	public SectionsDialog(Context context) {
		super(context);
		this.sectionsListView = new SectionsListView(context, null);
		this.setView(sectionsListView);
		this.setTitle("Choose sections");
	}

	public void setSections(List<Enrollment> enrollments) {
		this.sectionsListView.setCourses(enrollments);
	}

	public List<String> getSelectedSections() {
		ArrayList<String> sectionIDs = new ArrayList<String>(); 
		for (Enrollment e : this.sectionsListView.getSelectedSections()) {
			sectionIDs.add(e.getSectionID());
		}
		return sectionIDs;
	}

}
