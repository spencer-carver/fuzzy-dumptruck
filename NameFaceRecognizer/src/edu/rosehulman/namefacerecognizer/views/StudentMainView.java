package edu.rosehulman.namefacerecognizer.views;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.rosehulman.namefacerecognizer.R;
import edu.rosehulman.namefacerecognizer.model.Student;
import edu.rosehulman.namefacerecognizer.services.PersistenceService;

public class StudentMainView extends LinearLayout{

	private ImageView mainImage;
	private Student student;
	private TextView studentNameView;
	private TextView studentCourseView;
	private TextView studentNotes;
	
	public StudentMainView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = View.inflate(context, R.layout.student_view, null);
		this.addView(view);
		mainImage = (ImageView) view.findViewById(R.id.featured_picture);
		studentNameView = (TextView) view.findViewById(R.id.student_name);
		studentCourseView = (TextView) view.findViewById(R.id.student_course);
		studentNotes = (TextView) view.findViewById(R.id.student_notes);
	}

	public void setStudent(Student student) {
		this.student = student;
		Bitmap studentImage = PersistenceService.getInstance(getContext()).getStudentPicture(student);
		mainImage.setImageBitmap(studentImage);
		studentNameView.setText(student.getName());
		List<String> coursesList = student.getStudentCourses();
		if (coursesList.isEmpty()) {
			studentCourseView.setText(""); //student.getCourse());			
		} else {
			StringBuilder studentCourses = new StringBuilder(getCourseNumber(coursesList.get(0)));
			for (int i=1; i<coursesList.size(); i++ ) {

				studentCourses.append(", ");
				studentCourses.append(getCourseNumber(coursesList.get(i)));
			}
			studentCourseView.setText(studentCourses);
		}
		studentNotes.setText(student.getNote());
	}
	
	private String getCourseNumber(String fullCourseName) {
		String courseNumber = fullCourseName.trim();
		int indexOfSpace = courseNumber.indexOf(' ');
		if (indexOfSpace > -1) {
			courseNumber = courseNumber.substring(0, indexOfSpace);
		}		
		return courseNumber;
	}
	
	public Student getStudent() {
		return this.student;
	}

}
