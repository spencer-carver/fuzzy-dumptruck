package edu.rosehulman.namefacerecognizer.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import edu.rosehulman.namefacerecognizer.model.Enrollment;
import edu.rosehulman.namefacerecognizer.model.Student;

public class DBAdapter {

	// Database properties
	private static final String DATABASE_NAME = "students.db";
	private static final int DATABASE_VERSION = 1;
	
	// Students table properties
	private static final String STUDENTS_TABLE_NAME = "students";
	private static final String STUDENTS_ID_KEY = "student_id";
	private static final int STUDENTS_ID_COLUMN = 0;
	private static final String STUDENTS_FIRST_NAME_KEY = "fname";
	private static final int STUDENTS_FIRST_NAME_COLUMN = 1;
	private static final String STUDENTS_LAST_NAME_KEY = "lname";
	private static final int STUDENTS_LAST_NAME_COLUMN = 2;
	private static final String STUDENTS_NICKNAME_KEY = "nname";
	private static final int STUDENTS_NICKNAME_COLUMN = 3;
	private static final String STUDENTS_IMAGE_KEY = "picture";
	private static final int STUDENTS_IMAGE_COLUMN = 4;
	private static final String STUDENTS_NOTE_KEY = "note";
	private static final int STUDENTS_NOTE_COLUMN = 5;
	private static final String STUDENTS_USERNAME_KEY = "username";
	private static final int STUDENTS_USERNAME_COLUMN = 6;
	private static final String STUDENTS_NUM_GUESSED_KEY = "num_guessed";
	private static final int STUDENTS_NUM_GUESSED_COLUMN = 7;
	private static final String STUDENTS_NUM_TOTAL_KEY = "num_total";
	private static final int STUDENTS_NUM_TOTAL_COLUMN = 8;
	private static final String STUDENTS_E_VALUE = "e_value";
	private static final int STUDENTS_E_VALUE_COLUMN = 9;

	// Sections table properties
	private static final String SECTIONS_TABLE_NAME = "sections";
	private static final String SECTIONS_ID_KEY = "section_id";
	private static final int SECTIONS_ID_COLUMN = 0;
	private static final String SECTIONS_TITLE_KEY = "section_name";
	private static final int SECTIONS_TITLE_COLUMN = 1;
	private static final String SECTIONS_CATEGORY_KEY = "section_category";
	private static final int SECTIONS_CATEGORY_COLUMN = 2;
	private static final String SECTIONS_PROFESSOR_KEY = "section_teacher_username";
	private static final int SECTIONS_PROFESSOR_COLUMN = 3;
	
	// SectionStudents table properties
	private static final String SECTION_STUDENTS_TABLE_NAME = "section_students";
	private static final String SECTION_STUDENTS_SECTION_KEY = "sectionId";
	private static final int SECTION_STUDENTS_SECTION_COLUMN = 0;
	private static final String SECTION_STUDENTS_STUDENT_KEY = "student_username";
	private static final int SECTION_STUDENTS_STUDENT_COLUMN = 1;
	
	// Queries
	private static final String SELECTION_BY_STUDENT_ID = STUDENTS_ID_KEY + "=?";
	private static final String SELECTION_BY_SECTION_ID = SECTIONS_ID_KEY + "=?";
	private static final String SELECTION_BY_STUDENT_USERNAME = STUDENTS_USERNAME_KEY + "=?";
	
	private DBHelper mOpenHelper;
	private SQLiteDatabase mDb;

	private static class DBHelper extends SQLiteOpenHelper {

		private static String CREATE_STUDENTS_STATEMENT;
		private static String CREATE_SECTIONS_STATEMENT;
		private static String CREATE_SECTION_STUDENTS_STATEMENT;
		private static String DROP_STUDENTS_STATEMENT = "DROP TABLE IF EXISTS "
				+ STUDENTS_TABLE_NAME;
		private static String DROP_SECTIONS_STATEMENT = "DROP TABLE IF EXISTS "
				+ SECTIONS_TABLE_NAME;
		private static String DROP_SECTION_STUDENTS_STATEMENT = "DROP TABLE IF EXISTS "
				+ SECTION_STUDENTS_TABLE_NAME;
		
		static {
			StringBuilder s = new StringBuilder();
			s.append("CREATE TABLE ");
			s.append(STUDENTS_TABLE_NAME);
			s.append(" (");
			s.append(STUDENTS_ID_KEY);
			s.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
			s.append(STUDENTS_FIRST_NAME_KEY);
			s.append(" TEXT, ");
			s.append(STUDENTS_LAST_NAME_KEY);
			s.append(" TEXT, ");
			s.append(STUDENTS_NICKNAME_KEY);
			s.append(" TEXT, ");
			s.append(STUDENTS_IMAGE_KEY);
			s.append(" BLOB, ");
			s.append(STUDENTS_NOTE_KEY);
			s.append(" TEXT, ");
			s.append(STUDENTS_USERNAME_KEY);
			s.append(" TEXT UNIQUE, ");
			s.append(STUDENTS_NUM_GUESSED_KEY);
			s.append(" INTEGER, ");
			s.append(STUDENTS_NUM_TOTAL_KEY);
			s.append(" INTEGER, ");
			s.append(STUDENTS_E_VALUE);
			s.append(" FLOAT)");
			CREATE_STUDENTS_STATEMENT = s.toString();
			
			StringBuilder sbSection = new StringBuilder();
			sbSection.append("CREATE TABLE ");
			sbSection.append(SECTIONS_TABLE_NAME);
			sbSection.append(" (");
			sbSection.append(SECTIONS_ID_KEY);
			sbSection.append(" TEXT PRIMARY KEY, ");
			sbSection.append(SECTIONS_TITLE_KEY);
			sbSection.append(" TEXT, ");
			sbSection.append(SECTIONS_CATEGORY_KEY);
			sbSection.append(" TEXT, ");
			sbSection.append(SECTIONS_PROFESSOR_KEY);
			sbSection.append(" TEXT)");
			CREATE_SECTIONS_STATEMENT = sbSection.toString();
			
			StringBuilder sbSectionStudents = new StringBuilder();
			sbSectionStudents.append("CREATE TABLE ");
			sbSectionStudents.append(SECTION_STUDENTS_TABLE_NAME);
			sbSectionStudents.append(" (");
			sbSectionStudents.append(SECTION_STUDENTS_SECTION_KEY);
			sbSectionStudents.append(" TEXT, ");
			sbSectionStudents.append(SECTION_STUDENTS_STUDENT_KEY);
			sbSectionStudents.append(" TEXT, ");
			sbSectionStudents.append(" FOREIGN KEY(");
			sbSectionStudents.append(SECTION_STUDENTS_SECTION_KEY);
			sbSectionStudents.append(") REFERENCES ");
			sbSectionStudents.append(SECTIONS_TABLE_NAME);
			sbSectionStudents.append("(");
			sbSectionStudents.append(SECTIONS_ID_KEY);
			sbSectionStudents.append("), ");
			sbSectionStudents.append(" FOREIGN KEY(");
			sbSectionStudents.append(SECTION_STUDENTS_STUDENT_KEY);
			sbSectionStudents.append(") REFERENCES ");
			sbSectionStudents.append(STUDENTS_TABLE_NAME);
			sbSectionStudents.append("(");
			sbSectionStudents.append(STUDENTS_USERNAME_KEY);
			sbSectionStudents.append("))");
			
			CREATE_SECTION_STUDENTS_STATEMENT = sbSectionStudents.toString();
			
		}

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_SECTIONS_STATEMENT);
			db.execSQL(CREATE_STUDENTS_STATEMENT);
			db.execSQL(CREATE_SECTION_STUDENTS_STATEMENT);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DROP_SECTIONS_STATEMENT);
			db.execSQL(DROP_STUDENTS_STATEMENT);
			db.execSQL(DROP_SECTION_STUDENTS_STATEMENT);
			onCreate(db);
		}
	}

	public DBAdapter(Context context) {
		mOpenHelper = new DBHelper(context);
	}

	public void open() throws SQLiteException {
		mDb = mOpenHelper.getWritableDatabase();
	}

	public void close() {
		mDb.close();
	}
	
	public void resetDB() {
		mOpenHelper.onUpgrade(mDb, 1, 1);
	}

	private ContentValues getContentValuesFromStudentInfo(Student student) {
		ContentValues rowValues = new ContentValues();
		rowValues.put(STUDENTS_FIRST_NAME_KEY, student.getFirstName());
		rowValues.put(STUDENTS_LAST_NAME_KEY, student.getLastName());
		rowValues.put(STUDENTS_NICKNAME_KEY, student.getNickName());
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		rowValues.put(STUDENTS_IMAGE_KEY, out.toByteArray());
		rowValues.put(STUDENTS_NOTE_KEY, student.getNote());
		rowValues.put(STUDENTS_USERNAME_KEY, student.getUsername());
		rowValues.put(STUDENTS_NUM_GUESSED_KEY, student.getNumGuessed());
		rowValues.put(STUDENTS_NUM_TOTAL_KEY, student.getNumTotal());
		rowValues.put(STUDENTS_E_VALUE, student.getEValue());
		return rowValues;
	}

	private Student getStudentInfoFromCursor(Cursor cursor) {
		Student student = new Student();
		student.setID(cursor.getInt(STUDENTS_ID_COLUMN));
		student.setFirstName(cursor.getString(STUDENTS_FIRST_NAME_COLUMN));
		student.setLastName(cursor.getString(STUDENTS_LAST_NAME_COLUMN));
		student.setNickName(cursor.getString(STUDENTS_NICKNAME_COLUMN));
//		byte[] blob = cursor.getBlob(STUDENTS_IMAGE_COLUMN);
//		Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0,blob.length, null);
//		studentInfo.setPicture(bmp);
		student.setNote(cursor.getString(STUDENTS_NOTE_COLUMN));
		student.setUsername(cursor.getString(STUDENTS_USERNAME_COLUMN));
		
		student.setNumGuessed(cursor.getInt(STUDENTS_NUM_GUESSED_COLUMN));
		student.setNumTotal(cursor.getInt(STUDENTS_NUM_TOTAL_COLUMN));
		student.setEValue(cursor.getDouble(STUDENTS_E_VALUE_COLUMN));
		return student;
	}

	private Enrollment getEnrollmentFromCursor(Cursor cursor) {
		Enrollment enrollment = new Enrollment();
		enrollment.setSectionID(cursor.getString(SECTIONS_ID_COLUMN));
		enrollment.setSectionTitle(cursor.getString(SECTIONS_TITLE_COLUMN));
		enrollment.setSectionCategory(cursor.getString(SECTIONS_CATEGORY_COLUMN));
		enrollment.setPersisted(true);
		return enrollment;
	}
	
	public Student getStudentByID(int id) {
		Cursor cursor = mDb.query(STUDENTS_TABLE_NAME, null, SELECTION_BY_STUDENT_ID, 
				new String[] {Integer.toString(id)}, null, null, null);
		Student student = getStudentInfoFromCursor(cursor);
		List<Enrollment> studentCourses = getSectionsForStudent(student.getUsername());
		for (Enrollment e : studentCourses) {
			student.addCourse(e.getSectionTitle());
		}
		return student;
	}

	public Student getStudentByUsername(String username) {
		Cursor cursor = mDb.query(STUDENTS_TABLE_NAME, null, SELECTION_BY_STUDENT_USERNAME, 
				new String[] {username}, null, null, null);
		if (cursor.moveToFirst()) {
			Student student = getStudentInfoFromCursor(cursor);
			List<Enrollment> studentCourses = getSectionsForStudent(student.getUsername());
			for (Enrollment e : studentCourses) {
				student.addCourse(e.getSectionTitle());
			}
			return student;
		}
		// no such student
		return null;
	}
	
	public byte[] getStudentImageData(int studentId) {
		Cursor cursor = mDb.query(STUDENTS_TABLE_NAME, null, SELECTION_BY_STUDENT_ID, 
				new String[] {Integer.toString(studentId)}, null, null, null);
		byte[] imageData = cursor.getBlob(STUDENTS_IMAGE_COLUMN);
		return imageData;
	}

	public byte[] getStudentImageData(String studentUsername) {
		Cursor cursor = mDb.query(STUDENTS_TABLE_NAME, null, SELECTION_BY_STUDENT_USERNAME, 
				new String[] {studentUsername}, null, null, null);
		cursor.moveToFirst();
		byte[] imageData = cursor.getBlob(STUDENTS_IMAGE_COLUMN);
		return imageData;
	}

	public void addStudentImageData(int studentId, byte[] imageData) {
		ContentValues args = new ContentValues();
		args.put(STUDENTS_IMAGE_KEY, imageData);
		mDb.update(STUDENTS_TABLE_NAME, args, SELECTION_BY_STUDENT_USERNAME, new String[] {Integer.toString(studentId)});
	}

	public void addStudentImageData(String studentUsername, byte[] imageData) {
		ContentValues args = new ContentValues();
		args.put(STUDENTS_IMAGE_KEY, imageData);
		mDb.update(STUDENTS_TABLE_NAME, args, SELECTION_BY_STUDENT_USERNAME, new String[] {studentUsername});
	}

	public Student addStudent(Student student) {
		ContentValues rowValues = getContentValuesFromStudentInfo(student);
		mDb.insertWithOnConflict(STUDENTS_TABLE_NAME, null, rowValues, SQLiteDatabase.CONFLICT_IGNORE);
		Cursor cursor = mDb.query(STUDENTS_TABLE_NAME, new String[] { STUDENTS_ID_KEY,
				STUDENTS_FIRST_NAME_KEY, STUDENTS_LAST_NAME_KEY, STUDENTS_NICKNAME_KEY, STUDENTS_IMAGE_KEY,
				STUDENTS_NOTE_KEY, STUDENTS_USERNAME_KEY, STUDENTS_NUM_GUESSED_KEY, STUDENTS_NUM_TOTAL_KEY, STUDENTS_E_VALUE }, null,
				null, null, null, STUDENTS_ID_KEY + " DESC", "1");
		cursor.moveToFirst();
		return getStudentInfoFromCursor(cursor);
	}

	public void updateStudent(Student student) {
		ContentValues rowValues = getContentValuesFromStudentInfo(student);
		mDb.update(STUDENTS_TABLE_NAME, rowValues, SELECTION_BY_STUDENT_USERNAME, new String[] {student.getUsername()});
	}
	
	public void deleteStudent(Student student) {
		mDb.delete(STUDENTS_TABLE_NAME, SELECTION_BY_STUDENT_ID,
				new String[] { Integer.toString(student.getID()) });
	}

	public List<Student> getAllStudents() {
		ArrayList<Student> list = new ArrayList<Student>();
		Cursor cursor = mDb.query(STUDENTS_TABLE_NAME, null, null, null, null, null,
				null);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			Student student = getStudentInfoFromCursor(cursor);
			cursor.moveToNext();
			list.add(student);
		}
		return list;

	}
	
	public boolean isSectionInDB(Enrollment e) {
		Cursor cursor = mDb.query(SECTIONS_TABLE_NAME, null, SELECTION_BY_SECTION_ID, 
				new String[] {e.getSectionID()}, null, null, null);
		if (cursor.getCount() > 0) {
			// the section is in the DB already
			return true;
		}
		return false;
	}
	
	public boolean isStudentInDB(Student s) {
		Cursor cursor = mDb.query(STUDENTS_TABLE_NAME, null, SELECTION_BY_STUDENT_USERNAME, 
				new String[] {s.getUsername()}, null, null, null);
		return (cursor.getCount() == 0);
	}
	
	public void addSection(Enrollment e, String professorUsername) {
		ContentValues rowValues = getContentValuesFromSection(e);
		rowValues.put(SECTIONS_PROFESSOR_KEY, professorUsername);
		mDb.insert(SECTIONS_TABLE_NAME, null, rowValues);
	}
	
	public ContentValues getContentValuesFromSection(Enrollment e) {
		ContentValues rowValues = new ContentValues();
		rowValues.put(SECTIONS_ID_KEY, e.getSectionID());
		rowValues.put(SECTIONS_TITLE_KEY, e.getSectionTitle());
		rowValues.put(SECTIONS_CATEGORY_KEY, e.getSectionCategory());
		return rowValues;
	}
	
	public void addStudentToSection(String sectionID, Student student) {
		ContentValues rowValues = new ContentValues();
		rowValues.put(SECTION_STUDENTS_SECTION_KEY, sectionID);
		rowValues.put(SECTION_STUDENTS_STUDENT_KEY, student.getUsername());
		mDb.insert(SECTION_STUDENTS_TABLE_NAME, null, rowValues);
		
	}
	
	public List<Student> getStudentsForSection(String sectionID) {
		Cursor cursor = mDb.query(SECTION_STUDENTS_TABLE_NAME, new String[] {SECTION_STUDENTS_STUDENT_KEY},
				SECTION_STUDENTS_SECTION_KEY + "=?", new String[] {sectionID}, null, null, null);
		cursor.moveToFirst();
		List<Student> result = new ArrayList<Student>();
		while(!cursor.isAfterLast()) {
			String studentUsername = cursor.getString(cursor.getColumnIndex(SECTION_STUDENTS_STUDENT_KEY));
			Student student = getStudentByUsername(studentUsername);
			result.add(student);
			cursor.moveToNext();
		}
		
		return result;
	}
	
	public List<Enrollment> getSectionsForProfessor(String professorUsername) {
		List<Enrollment> result = new ArrayList<Enrollment>();
		Cursor cursor = mDb.query(SECTIONS_TABLE_NAME, null, SECTIONS_PROFESSOR_KEY + "=?", new String[] {professorUsername}, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			Enrollment enrollment = getEnrollmentFromCursor(cursor);
			cursor.moveToNext();
			result.add(enrollment);
		}
		
		return result;
	}
	
	public Enrollment getEnrollment(String enrollmentID) {
		Cursor cursor = mDb.query(SECTIONS_TABLE_NAME, null, SELECTION_BY_SECTION_ID, new String[] {enrollmentID}, null, null, null);
		if(cursor.moveToFirst())
			return getEnrollmentFromCursor(cursor);
		else
			return null; // no such enrollment in the DB
	}
	
	public List<Enrollment> getSectionsForStudent(String username) {
		List<Enrollment> result = new ArrayList<Enrollment>();
		Cursor cursor = mDb.query(SECTION_STUDENTS_TABLE_NAME, new String[] {SECTION_STUDENTS_SECTION_KEY},
				SECTION_STUDENTS_STUDENT_KEY + "=?", new String[] {username}, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			String enrollmentID = cursor.getString(cursor.getColumnIndex(SECTION_STUDENTS_SECTION_KEY));
			Enrollment student = getEnrollment(enrollmentID);
			result.add(student);
			cursor.moveToNext();
		}
		
		return result;
	}
}
