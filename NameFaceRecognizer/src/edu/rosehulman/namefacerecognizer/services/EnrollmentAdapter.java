package edu.rosehulman.namefacerecognizer.services;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import edu.rosehulman.namefacerecognizer.R;
import edu.rosehulman.namefacerecognizer.activities.DownloadActivity;
import edu.rosehulman.namefacerecognizer.model.Enrollment;

public class EnrollmentAdapter extends ArrayAdapter<Enrollment> {
	
	private final Activity mContext;
	private final int mResource;
	private final List<Enrollment> mEnrollments;

	public EnrollmentAdapter(Activity context, int resource, List<Enrollment> objects) {
		super(context, resource, objects);
		mContext = context;
		mResource = resource;
		mEnrollments = objects;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = mContext.getLayoutInflater();
		View v = inflater.inflate(mResource, null, true);
		TextView sectionName = (TextView) v.findViewById(R.id.sectionName);
		TextView sectionCategory = (TextView) v.findViewById(R.id.sectionCategory);
		CheckBox downloaded = (CheckBox) v.findViewById(R.id.check);

		Enrollment course = mEnrollments.get(position);
		sectionName.setText(course.getSectionTitle());
		sectionCategory.setText(course.getSectionCategory());
		downloaded.setChecked(course.isSelected());
		downloaded.setOnCheckedChangeListener(new OnCheckedChangeListener() { 

		    public void onCheckedChanged(CompoundButton buttonView, 
		                                            boolean isChecked) { 
		      if (buttonView.isChecked()) { 
		    	  ((DownloadActivity) mContext).incrementNumSelected(position);
		    	  mEnrollments.get(position).setSelected(true);
		    	  //TODO make sure the position is recorded to be kept later
		      } 
		      else 
		      { 
		    	  ((DownloadActivity) mContext).decrementNumSelected(position);
		    	  mEnrollments.get(position).setSelected(false);
		    	  //TODO make sure the position is recorded to be kept later
		      } 
		    }

		  }); 
		return v;
	}
	
	

}
