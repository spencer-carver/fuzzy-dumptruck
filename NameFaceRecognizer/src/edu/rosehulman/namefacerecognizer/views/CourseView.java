package edu.rosehulman.namefacerecognizer.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import edu.rosehulman.namefacerecognizer.R;
import edu.rosehulman.namefacerecognizer.model.Enrollment;

public class CourseView extends RelativeLayout {

	public static final int LIST_VIEW_RESOURSE = R.layout.course_listview;
	
	private Enrollment course;
	
	private TextView sectionName;
	private TextView sectionCategory;
	private CheckBox downloaded;
		
	public CourseView(Context context, AttributeSet attrs) {
		this(context, attrs, null);
	}
	
	public CourseView(Context context, AttributeSet attrs, ViewGroup parent) {
		super(context, attrs);
		View v =  ((LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE ))
				.inflate(LIST_VIEW_RESOURSE, parent, false);
		this.addView(v);
		this.sectionName = (TextView) v.findViewById(R.id.sectionName);
		this.sectionCategory = (TextView) v.findViewById(R.id.sectionCategory);
		this.downloaded = (CheckBox) v.findViewById(R.id.check);
	}
	
	public void setDisplayedCourse(Enrollment course) {
		this.course = course;
		this.sectionName.setText(this.course.getSectionTitle());
		this.sectionCategory.setText(this.course.getSectionCategory());
		// TODO: Enable after testing is done
		// this.downloaded.setChecked(!this.course.isPersisted()); 
	}
	
	public void setCheckedState(boolean newState) {
		downloaded.setChecked(newState);
	}
	
	public boolean getCheckedState() {
		boolean checkBoxMarked = downloaded.isChecked();
		return checkBoxMarked;
	}

	/**
	 * 
	 * @return True if the new state is selected; else false
	 */
	public boolean switchCheckedState() {
		boolean checkBoxMarked = downloaded.isChecked();
		this.downloaded.setChecked(!checkBoxMarked);
		return !checkBoxMarked;
	}
}
