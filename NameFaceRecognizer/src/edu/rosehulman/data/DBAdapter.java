package edu.rosehulman.data;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.nameface.model.Student;
import edu.rosehulman.nameface.model.StudentInfo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DBAdapter {

	private static final String DATABASE_NAME = "students.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "students";
	private static final String ID_KEY = "id";
	private static final int ID_COLUMN = 0;
	private static final String FIRST_NAME_KEY = "fname";
	private static final int FIRST_NAME_COLUMN = 1;
	private static final String LAST_NAME_KEY = "lname";
	private static final int LAST_NAME_COLUMN = 2;
	private static final String NICKNAME_KEY = "nname";
	private static final int NICKNAME_COLUMN = 3;
	private static final String IMAGE_KEY = "picture";
	private static final int IMAGE_COLUMN = 4;
	private static final String NOTE_KEY = "note";
	private static final int NOTE_COLUMN = 5;
	private static final String COURSE_KEY = "course";
	private static final int COURSE_COLUMN = 6;
	private static final String NUM_GUESSED_KEY = "num_guessed";
	private static final int NUM_GUESSED_COLUMN = 7;
	private static final String NUM_TOTAL_KEY = "num_total";
	private static final int NUM_TOTAL_COLUMN = 8;

	private DBHelper mOpenHelper;
	private SQLiteDatabase mDb;

	private static class DBHelper extends SQLiteOpenHelper {

		private static String CREATE_STATEMENT;
		private static String DROP_STATEMENT = "DROP TABLE IF EXISTS "
				+ TABLE_NAME;
		static {
			StringBuilder s = new StringBuilder();
			s.append("CREATE TABLE ");
			s.append(TABLE_NAME);
			s.append(" (");
			s.append(ID_KEY);
			s.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
			s.append(FIRST_NAME_KEY);
			s.append(" TEXT, ");
			s.append(LAST_NAME_KEY);
			s.append(" TEXT, ");
			s.append(NICKNAME_KEY);
			s.append(" TEXT, ");
			s.append(IMAGE_KEY);
			s.append(" BLOB, ");
			s.append(NOTE_KEY);
			s.append(" TEXT, ");
			s.append(COURSE_KEY);
			s.append(" TEXT, ");
			s.append(NUM_GUESSED_KEY);
			s.append(" INTEGER, ");
			s.append(NUM_TOTAL_KEY);
			s.append(" INTEGER)");
			CREATE_STATEMENT = s.toString();
		}

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_STATEMENT);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DROP_STATEMENT);
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
		rowValues.put(FIRST_NAME_KEY, student.getFirstName());
		rowValues.put(LAST_NAME_KEY, student.getLastName());
		rowValues.put(NICKNAME_KEY, student.getNickName());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		student.getPicture().compress(Bitmap.CompressFormat.PNG, 100,out);
		rowValues.put(IMAGE_KEY, out.toByteArray());
		rowValues.put(NOTE_KEY, student.getNote());
		rowValues.put(COURSE_KEY, student.getCourse());
		rowValues.put(NUM_GUESSED_KEY, student.getNumGuessed());
		rowValues.put(NUM_TOTAL_KEY, student.getNumTotal());
		return rowValues;
	}

	private Student getStudentInfoFromCursor(Cursor cursor) {
		Student student = new Student();
		StudentInfo studentInfo = new StudentInfo();
		studentInfo.setID(cursor.getInt(ID_COLUMN));
		studentInfo.setFirstName(cursor.getString(FIRST_NAME_COLUMN));
		studentInfo.setLastName(cursor.getString(LAST_NAME_COLUMN));
		studentInfo.setNickName(cursor.getString(NICKNAME_COLUMN));
		byte[] blob = cursor.getBlob(IMAGE_COLUMN);
		Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0,blob.length, null);
		studentInfo.setPicture(bmp);
		studentInfo.setNote(cursor.getString(NOTE_COLUMN));
		studentInfo.setCourse(cursor.getString(COURSE_COLUMN));
		
		student.setStudentInfo(studentInfo);
		student.setNumGuessed(cursor.getInt(NUM_GUESSED_COLUMN));
		student.setNumTotal(cursor.getInt(NUM_TOTAL_COLUMN));
		return student;
	}

	public Student addStudent(Student student) {
		ContentValues rowValues = getContentValuesFromStudentInfo(student);
		mDb.insert(TABLE_NAME, null, rowValues);
		Cursor cursor = mDb.query(TABLE_NAME, new String[] { ID_KEY,
				FIRST_NAME_KEY, LAST_NAME_KEY, NICKNAME_KEY, IMAGE_KEY,
				NOTE_KEY, COURSE_KEY, NUM_GUESSED_KEY, NUM_TOTAL_KEY }, null,
				null, null, null, ID_KEY + " DESC", "1");
		cursor.moveToFirst();
		return getStudentInfoFromCursor(cursor);
	}

	public void deleteStudent(StudentInfo student) {
		mDb.delete(TABLE_NAME, ID_KEY + " = ?",
				new String[] { Integer.toString(student.getID()) });
	}

	public List<Student> getAllStudents() {
		ArrayList<Student> list = new ArrayList<Student>();
		Cursor cursor = mDb.query(TABLE_NAME, null, null, null, null, null,
				null);
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++) {
			Student student = getStudentInfoFromCursor(cursor);
			cursor.moveToNext();
			list.add(student);
		}
		return list;

	}
}
