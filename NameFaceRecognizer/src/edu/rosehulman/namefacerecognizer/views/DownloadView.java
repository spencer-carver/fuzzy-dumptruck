package edu.rosehulman.namefacerecognizer.views;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import edu.rosehulman.namefacerecognizer.R;
import edu.rosehulman.namefacerecognizer.model.Enrollment;
import edu.rosehulman.namefacerecognizer.views.SectionsListView.SelectedSectionsChangeListener;

public class DownloadView extends RelativeLayout {

	private Button mDownloadButton;
	private Button mBackButton;
	
	private SectionsListView sectionsListView; 
	private DownloadViewListener listener;
	private int mNumSelected;
	
	public static interface DownloadViewListener {
		public void onDownloadButtonClicked();
		public void onBackButtonClicked();
	}
	
	public DownloadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mNumSelected = 0;
		View view = View.inflate(context, R.layout.activity_download, null);
		this.addView(view);
		this.mDownloadButton = (Button) view.findViewById(R.id.download_button);
		this.mDownloadButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				listener.onDownloadButtonClicked();
			}
		});
		this.mDownloadButton.setClickable(false);
		this.mDownloadButton.setTextColor(getResources().getColor(R.color.gray));

		this.mBackButton = (Button) view.findViewById(R.id.back_button);
		this.mBackButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				listener.onBackButtonClicked();
			} 
		});

		this.sectionsListView = (SectionsListView)view.findViewById(R.id.sections_list_view);
		this.sectionsListView.setOnSelectedSectionsChangeListener(new SelectedSectionsChangeListener() {
			
			public void onSectionSelected(Enrollment e) {
				incrementNumSelected();
			}
			
			public void onSectionDeselected(Enrollment e) {
				decrementNumSelected();
			}
		});
			
	}

	public void setListener(DownloadViewListener listener) {
		this.listener = listener;
	}
	
	public void incrementNumSelected() {
		if (this.mNumSelected == 0) {
			mDownloadButton.setClickable(true);
			mDownloadButton.setTextColor(getResources().getColor(R.color.black));
		}
		this.mNumSelected++;
		mDownloadButton.setText("Download (" + this.mNumSelected + ")");
	}
	
	public void decrementNumSelected() {
		this.mNumSelected--;
		mDownloadButton.setText("Download (" + this.mNumSelected + ")");
		if (this.mNumSelected == 0) {
			mDownloadButton.setClickable(false);
			mDownloadButton.setTextColor(getResources().getColor(R.color.gray));
		}
	}

	public List<Enrollment> getCoursesForDownload() {
		return this.sectionsListView.getSelectedSections();
	}

	public void setCourses(List<Enrollment> allCourses) {
		this.sectionsListView.setCourses(allCourses);
	}
}
