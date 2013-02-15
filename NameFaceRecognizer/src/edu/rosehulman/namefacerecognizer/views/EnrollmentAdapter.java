package edu.rosehulman.namefacerecognizer.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import edu.rosehulman.namefacerecognizer.model.Enrollment;

public class EnrollmentAdapter extends ArrayAdapter<Enrollment> {
	
	// private List<Enrollment> mEnrollments;
	private List<Boolean> positionsChecked;
	
	public EnrollmentAdapter(Context context, List<Enrollment> objects) {
		super(context, CourseView.LIST_VIEW_RESOURSE, objects);
		// mEnrollments = objects;
		positionsChecked = new ArrayList<Boolean>(objects.size());
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Enrollment course = getItem(position); //mEnrollments.get(position);
		
		if (convertView == null || !(convertView instanceof CourseView)) {
			CourseView view = new CourseView(this.getContext(), null, parent);
			view.setDisplayedCourse(course);
			return view;
		} else {
			// it's ok to reuse the view
			CourseView oldView = (CourseView)convertView;
			oldView.setDisplayedCourse(course);
			oldView.setCheckedState(positionsChecked.get(position));
			return oldView;
		}

	}
	
	public void itemSelected(final int position) {
		positionsChecked.set(position, true);
	}
	
	public void itemDeselected(final int position) {
		positionsChecked.set(position, false);
	}
	
	@Override
	public void add(Enrollment object) {
		super.add(object);
		positionsChecked.add(false);
	}
	
	@Override
	public void addAll(Collection<? extends Enrollment> collection) {
		super.addAll(collection);
		int numberOfNewElements = this.getCount() - positionsChecked.size();
		while(numberOfNewElements > 0) {
			numberOfNewElements--;
			positionsChecked.add(false);
		}
	}
}
