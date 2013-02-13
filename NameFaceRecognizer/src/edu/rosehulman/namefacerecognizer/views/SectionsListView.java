package edu.rosehulman.namefacerecognizer.views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import edu.rosehulman.namefacerecognizer.model.Enrollment;

public class SectionsListView extends ListView {

	private EnrollmentAdapter mCourseAdapter;
//	private int mNumSelected;
	private Set<Enrollment> mSelectedCourses;
	private List<Enrollment> mCourses;

	private SelectedSectionsChangeListener listener;
	
	public static interface SelectedSectionsChangeListener {
		public void onSectionSelected(Enrollment e);
		public void onSectionDeselected(Enrollment e);
	}
	
	public SectionsListView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.mSelectedCourses = new HashSet<Enrollment>();
		this.mCourses = new ArrayList<Enrollment>();
		this.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
		this.mCourseAdapter = new EnrollmentAdapter(context, mCourses);
		this.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Enrollment selectedEnrollment = mCourses.get(position);
				if (mSelectedCourses.contains(selectedEnrollment)) {
					onSectionDeselected(selectedEnrollment);

				} else {
					onSectionSelected(selectedEnrollment);
					
				}

				((CourseView)view).switchCheckedState();
				
			}
			
		});

		this.setAdapter(mCourseAdapter);
	}

	public void setOnSelectedSectionsChangeListener(SelectedSectionsChangeListener listener) {
		this.listener = listener;
	}
	
	private void onSectionSelected(Enrollment e) {
		mSelectedCourses.add(e);
//		incrementNumSelected();
		if(this.listener != null) {
			listener.onSectionSelected(e);
		}

	}
	private void onSectionDeselected(Enrollment e) {
		mSelectedCourses.remove(e);
//		decrementNumSelected();
		if(this.listener != null) {
			listener.onSectionDeselected(e);
		}
	}
	
//	private void incrementNumSelected() {
//		mNumSelected++;
//	}
//	
//	public void decrementNumSelected() {
//		mNumSelected--;
//	}

	public List<Enrollment> getSelectedSections() {
		List<Enrollment> selectedCourses = new ArrayList<Enrollment>(mSelectedCourses);
		return selectedCourses;
	}

	public void setCourses(List<Enrollment> allCourses) {
		this.mCourses = allCourses;
		this.mSelectedCourses.clear();
		this.mCourseAdapter.clear();
		this.mCourseAdapter.addAll(allCourses);
	}

}