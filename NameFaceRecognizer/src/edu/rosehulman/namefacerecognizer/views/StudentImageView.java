package edu.rosehulman.namefacerecognizer.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import edu.rosehulman.namefacerecognizer.R;
import edu.rosehulman.namefacerecognizer.model.Student;
import edu.rosehulman.namefacerecognizer.services.PersistenceService;

public class StudentImageView extends LinearLayout{

	private ImageView image;
	private Student student;
	
	public StudentImageView(Context context, AttributeSet attrs, Student student) {
		super(context, attrs);
		View view = View.inflate(context, R.layout.filmstrip_view, null);
		this.addView(view);
		this.student = student;
		Bitmap studentImage = PersistenceService.getInstance(getContext()).getStudentPicture(student);
		image = (ImageView) view.findViewById(R.id.filmstrip_picture);
		image.setImageBitmap(studentImage);
		
	}
	
	public Student getStudent() {
		return this.student;
	}

}
