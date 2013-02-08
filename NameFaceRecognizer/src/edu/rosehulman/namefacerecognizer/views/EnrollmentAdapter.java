package edu.rosehulman.namefacerecognizer.views;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import edu.rosehulman.namefacerecognizer.model.Enrollment;

public class EnrollmentAdapter extends ArrayAdapter<Enrollment> {
	
	private List<Enrollment> mEnrollments;

	public EnrollmentAdapter(Context context, List<Enrollment> objects) {
		super(context, CourseView.LIST_VIEW_RESOURSE, objects);
		mEnrollments = objects;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Enrollment course = mEnrollments.get(position);
		
		if (convertView == null || !(convertView instanceof CourseView)) {
			CourseView view = new CourseView(this.getContext(), null, parent);
			view.setDisplayedCourse(course);
			return view;
		} else {
			// it's ok to reuse the view
			CourseView oldView = (CourseView)convertView;
			oldView.setDisplayedCourse(course);
			return oldView;
		}

	}
	
	

}
