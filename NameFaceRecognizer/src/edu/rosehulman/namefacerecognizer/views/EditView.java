package edu.rosehulman.namefacerecognizer.views;

import edu.rosehulman.namefacerecognizer.R;
import edu.rosehulman.namefacerecognizer.model.Student;
import edu.rosehulman.namefacerecognizer.services.PersistenceService;
import edu.rosehulman.namefacerecognizer.utils.BitmapUtils;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class EditView extends RelativeLayout {

	private Student studentForEdit;
	
	private ImageView pictureView;
	private EditText studentNameView;
	private EditText studentCourseView;
	private EditText studentNicknameView;
	private EditText studentNotesView;
	
	private Button cancelButton;
	private Button doneButton;
	
	private EditViewListener editViewListener;
	
	public static interface EditViewListener {
		public void onCancelRequested();
		public void onChangeRequested(Student student, String newNickname, String newNotes);
	}
	
	public EditView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = View.inflate(context, R.layout.activity_edit_student, null);
		addView(view);
		pictureView = (ImageView) view.findViewById(R.id.edit_picture);
		studentNameView = (EditText) view.findViewById(R.id.edit_student_name);
		studentCourseView = (EditText) view.findViewById(R.id.edit_student_course);
		studentNicknameView = (EditText) view.findViewById(R.id.edit_student_nickname);
		studentNotesView = (EditText) view.findViewById(R.id.edit_student_notes);
		
		cancelButton = (Button) view.findViewById(R.id.edit_cancel_button);
		cancelButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				editViewListener.onCancelRequested();
			}
		});
		
		doneButton = (Button) view.findViewById(R.id.edit_submit_button);
		doneButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				editViewListener.onChangeRequested(studentForEdit, studentNicknameView.getText().toString(), 
						studentNotesView.getText().toString());
			}
		});
	}
	
	public void setListener(EditViewListener listener) {
		this.editViewListener = listener;
	}
	
	public void setStudent(Student student) {
		this.studentForEdit = student;
		byte[] imageData = PersistenceService.getInstance(getContext()).getStudentImageData(studentForEdit);
		pictureView.setImageBitmap(BitmapUtils.loadBitmap(imageData));
		studentNameView.setText(studentForEdit.getName());
//		studentCourseView.setText(studentForEdit.getCourse());
		studentNicknameView.setText(studentForEdit.getNickName());
		studentNotesView.setText(studentForEdit.getNote());
	}
}
