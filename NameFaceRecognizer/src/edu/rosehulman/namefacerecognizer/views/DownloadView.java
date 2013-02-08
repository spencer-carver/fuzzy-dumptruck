package edu.rosehulman.namefacerecognizer.views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import edu.rosehulman.namefacerecognizer.R;
import edu.rosehulman.namefacerecognizer.model.Enrollment;

public class DownloadView extends RelativeLayout {

	private ListView mCourseListView;
	private EnrollmentAdapter mCourseAdapter;
	private Button mDownloadButton;
	private Button mBackButton;
	
	private int mNumSelected;
	private Set<Enrollment> mSelectedCourses;
	private List<Enrollment> mCourses;
	
	private DownloadViewListener listener;
	
	public static interface DownloadViewListener {
		public void onDownloadButtonClicked();
		public void onBackButtonClicked();
	}
	
	public DownloadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = View.inflate(context, R.layout.activity_download, null);
		this.addView(view);
		this.mSelectedCourses = new HashSet<Enrollment>();
		this.mCourses = new ArrayList<Enrollment>();
		this.mDownloadButton = (Button) view.findViewById(R.id.download_button);
		this.mDownloadButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				listener.onDownloadButtonClicked();
			}
		});
		this.mBackButton = (Button) view.findViewById(R.id.back_button);
		this.mBackButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				listener.onBackButtonClicked();
			} 
		});
		this.mCourseListView = (ListView)view.findViewById(R.id.display_courses_list);
		this.mCourseListView.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
		this.mCourseAdapter = new EnrollmentAdapter(context, mCourses);
		this.mCourseListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Enrollment selectedEnrollment = mCourses.get(position);
				if (mSelectedCourses.contains(selectedEnrollment)) {
					// Used to be selected; unselect
					mSelectedCourses.remove(selectedEnrollment);
					decrementNumSelected();
				} else {
					// Used to be unselected; select
					mSelectedCourses.add(selectedEnrollment);
					incrementNumSelected();
				}
				((CourseView)view).switchCheckedState();
				
			}
			
		});
		this.mCourseListView.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Enrollment selectedEnrollment = mCourses.get(position);
				if (mSelectedCourses.contains(selectedEnrollment)) {
					// Used to be selected; unselect
					mSelectedCourses.remove(selectedEnrollment);
					decrementNumSelected();
				} else {
					// Used to be unselected; select
					mSelectedCourses.add(selectedEnrollment);
					incrementNumSelected();
				}
				((CourseView)view).switchCheckedState();
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		mCourseListView.setAdapter(mCourseAdapter);
	}

	public void setListener(DownloadViewListener listener) {
		this.listener = listener;
	}
	
	public void incrementNumSelected() {
		if (mNumSelected == 0) {
			mDownloadButton.setClickable(true);
			mDownloadButton.setTextColor(getResources().getColor(R.color.black));
		}
		mNumSelected++;
		mDownloadButton.setText("Download (" + mNumSelected + ")");
	}
	
	public void decrementNumSelected() {
		mNumSelected--;
		mDownloadButton.setText("Download (" + mNumSelected + ")");
		if (mNumSelected == 0) {
			mDownloadButton.setClickable(false);
			mDownloadButton.setTextColor(getResources().getColor(R.color.gray));
		}
	}

	public List<Enrollment> getCoursesForDownload() {
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
