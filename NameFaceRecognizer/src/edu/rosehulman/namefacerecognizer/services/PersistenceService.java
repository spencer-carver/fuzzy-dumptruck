package edu.rosehulman.namefacerecognizer.services;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.graphics.Bitmap;
import edu.rosehulman.namefacerecognizer.database.DBAdapter;
import edu.rosehulman.namefacerecognizer.model.Enrollment;
import edu.rosehulman.namefacerecognizer.model.Student;
import edu.rosehulman.namefacerecognizer.utils.BitmapUtils;

public class PersistenceService {
	
	private Context context;
	private DBAdapter dbA;
	private static PersistenceService instance;
	
	private PersistenceService(Context context) {
		this.context = context;
		this.dbA = new DBAdapter(this.context);
	}

	public static PersistenceService getInstance(Context context) {
		if(instance == null) {
			instance = new PersistenceService(context.getApplicationContext());
		}
		
		return instance;
	}
	
	public void persistSection(Enrollment enrollment) {

		dbA.open();
		dbA.addSection(enrollment);

		dbA.close();
	}

	public void persistStudentInfo(List<Student> students) {
		dbA.open();
		for (Student s : students) {
			dbA.addStudent(s);
		}
		dbA.close();
	}
	public void persistStudentPictures(Map<String, byte[]> studentPictures) {
		dbA.open();
		for(Entry<String, byte[]> studentPicture : studentPictures.entrySet()) {
			String studentUsername = studentPicture.getKey();
			byte[] pictureData = studentPicture.getValue();
			dbA.addStudentImageData(studentUsername, pictureData);
		}
		dbA.close();
	}
	
	public void persistStudentPicture(String username, byte[] picture) {
		dbA.open();
		dbA.addStudentImageData(username, picture);
		dbA.close();
	}
	
	public void markPersistedSections(List<Enrollment> sections) {
		for(Enrollment e : sections) {
			boolean persisted = checkSectionPersisted(e);
			e.setPersisted(persisted);
		}
	}
	
	private boolean checkSectionPersisted(Enrollment e) {
		this.dbA.open();
		boolean persisted = this.dbA.isSectionInDB(e);
		this.dbA.close();
		return persisted;
	}
	
	public byte[] getStudentImageData(Student student) {
		this.dbA.open();
		byte[] imageData = this.dbA.getStudentImageData(student.getUsername());
		this.dbA.close();
		return imageData;
	}
	
	public Bitmap getStudentPicture(Student student) {
		byte[] imageData = this.getStudentImageData(student);
		return BitmapUtils.loadBitmap(imageData);
	}
	
	public List<Student> getAllStudents() {
		this.dbA.open();
		List<Student> result = this.dbA.getAllStudents();
		this.dbA.close();
		return result;
	}
}
