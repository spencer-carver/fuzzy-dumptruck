package edu.rosehulman.namefacerecognizer.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import edu.rosehulman.namefacerecognizer.model.Student;

public class StudentImageView extends ImageView {

	private Student student;
	
	public StudentImageView(Context context, AttributeSet attrs, Student student) {
		super(context, attrs);
		this.student = student;
		this.setImageBitmap(student.getPicture());
	}

	public Student getStudent() {
		return this.student;
	}
}
