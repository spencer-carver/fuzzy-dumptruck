package edu.rosehulman.namefacerecognizer.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;
import edu.rosehulman.namefacerecognizer.model.Student;
import edu.rosehulman.namefacerecognizer.services.PersistenceService;

public class StudentImageView extends ImageView {

	private Student student;
	
	public StudentImageView(Context context, AttributeSet attrs, Student student) {
		super(context, attrs);
		this.student = student;
		Bitmap studentImage = PersistenceService.getInstance(context).getStudentPicture(student);
		this.setImageBitmap(studentImage);
	}

	public Student getStudent() {
		return this.student;
	}
}
