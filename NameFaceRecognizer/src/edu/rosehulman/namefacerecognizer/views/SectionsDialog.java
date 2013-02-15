package edu.rosehulman.namefacerecognizer.views;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import edu.rosehulman.namefacerecognizer.model.Enrollment;

public class SectionsDialog extends AlertDialog implements SectionsListView.SelectedSectionsChangeListener {

	private SectionsListView sectionsListView;
	private int numberOfSectionsSelected;
	
	public SectionsDialog(Context context) {
		super(context);
		this.sectionsListView = new SectionsListView(context, null);
		this.sectionsListView.setOnSelectedSectionsChangeListener(this);
		this.setView(sectionsListView);
		this.setTitle("Choose sections");
		this.numberOfSectionsSelected = 0;
	}
	
	@Override
	protected void onStart() {
		this.getButton(BUTTON_POSITIVE).setEnabled(false);
	};

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


	public void onSectionSelected(Enrollment e) {
		numberOfSectionsSelected++;
		this.getButton(BUTTON_POSITIVE).setEnabled(true);
	}


	public void onSectionDeselected(Enrollment e) {
		numberOfSectionsSelected--;
		if(numberOfSectionsSelected <= 0) {
			this.getButton(BUTTON_POSITIVE).setEnabled(false);
		}
	}

}
