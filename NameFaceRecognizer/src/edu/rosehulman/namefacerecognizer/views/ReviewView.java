package edu.rosehulman.namefacerecognizer.views;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import edu.rosehulman.namefacerecognizer.R;
import edu.rosehulman.namefacerecognizer.model.Review;
import edu.rosehulman.namefacerecognizer.model.Student;

public class ReviewView extends RelativeLayout {

	private Review review;
	private Student selectedStudent;

	private StudentMainView studentView;
	private LinearLayout filmstrip;
	private static int filmstrip_dimension = 150;
	
	public static interface ViewListener {
		// Nothing needed here for now
	}
	
	public ReviewView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = View.inflate(context, R.layout.activity_review, null);
		this.addView(view);
		studentView = (StudentMainView) view.findViewById(R.id.mainStudentView);
		filmstrip = (LinearLayout) view.findViewById(R.id.filmstrip);
	
	}
	
	public void setReviewData(Review reviewData) {
		this.review = reviewData;
		
		filmstrip.removeAllViews();
		List<Student> mStudents = this.review.getStudentsForReview();
		for (Student student : mStudents) {
			StudentImageView sView = new StudentImageView(this.getContext(), null, student);
			sView.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					setSelectedStudent(((StudentImageView) v).getStudent());
				}
			});
			filmstrip.addView(sView);
		}
		setSelectedStudent(mStudents.get(0));

	}
	
	public void setSelectedStudent(Student student) {
		this.selectedStudent = student;
		this.studentView.setStudent(this.selectedStudent);
		Log.d("IMG", ""+this.studentView.getHeight()+","+this.studentView.getWidth());
	}

	public Student getSelectedStudent() {
		return this.selectedStudent;
	}
}
