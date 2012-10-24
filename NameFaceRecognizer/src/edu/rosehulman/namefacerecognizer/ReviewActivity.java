package edu.rosehulman.namefacerecognizer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReviewActivity extends Activity implements OnClickListener {

	private ImageView mainImage;
	private TextView studentName;
	private TextView studentCourse;
	private LinearLayout filmstrip;
	
	
	private int filmstrip_dimension = 150;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review);
		mainImage = (ImageView) findViewById(R.id.featured_picture);
		studentName = (TextView) findViewById(R.id.student_name);
		studentCourse = (TextView) findViewById(R.id.student_course);
		filmstrip = (LinearLayout) findViewById(R.id.filmstrip);
		
		//TODO: Replace with dynamic generator for real code
		ImageView spencer = new ImageView(this);
		spencer.setId(R.string.spencer_id);
		spencer.setLayoutParams(new LinearLayout.LayoutParams(filmstrip_dimension, filmstrip_dimension));
		spencer.setImageResource(R.drawable.spencer_angel_pic);
		spencer.setOnClickListener(this);
		filmstrip.addView(spencer);
		ImageView frank = new ImageView(this);
		frank.setId(R.string.frank_id);
		frank.setLayoutParams(new LinearLayout.LayoutParams(filmstrip_dimension, filmstrip_dimension));
		frank.setImageResource(R.drawable.frank_angel_pic);
		frank.setOnClickListener(this);
		filmstrip.addView(frank);
		ImageView dylan = new ImageView(this);
		dylan.setId(R.string.dylan_id);
		dylan.setLayoutParams(new LinearLayout.LayoutParams(filmstrip_dimension, filmstrip_dimension));
		dylan.setImageResource(R.drawable.dylan_angel_pic);
		dylan.setOnClickListener(this);
		filmstrip.addView(dylan);
		ImageView marina = new ImageView(this);
		marina.setId(R.string.marina_id);
		marina.setLayoutParams(new LinearLayout.LayoutParams(filmstrip_dimension, filmstrip_dimension));
		marina.setImageResource(R.drawable.marina_angel_pic);
		marina.setOnClickListener(this);
		filmstrip.addView(marina);
		ImageView dan = new ImageView(this);
		dan.setId(R.string.dan_id);
		dan.setLayoutParams(new LinearLayout.LayoutParams(filmstrip_dimension, filmstrip_dimension));
		dan.setImageResource(R.drawable.dan_angel_pic);
		dan.setOnClickListener(this);
		filmstrip.addView(dan);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_review, menu);
		return true;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.string.spencer_id:
			mainImage.setImageResource(R.drawable.spencer_angel_pic);
			studentName.setText("Name: Spencer Carver");
			studentCourse.setText("Course: CSSE 490");
			break;
		case R.string.frank_id:
			mainImage.setImageResource(R.drawable.frank_angel_pic);
			studentName.setText("Name: Frank Huang");
			studentCourse.setText("Course: N/A");
			break;
		case R.string.dylan_id:
			mainImage.setImageResource(R.drawable.dylan_angel_pic);
			studentName.setText("Name: Dylan Kessler");
			studentCourse.setText("Course: N/A");
			break;
		case R.string.marina_id:
			mainImage.setImageResource(R.drawable.marina_angel_pic);
			studentName.setText("Name: Marina Kraeva");
			studentCourse.setText("Course: N/A");
			break;
		case R.string.dan_id:
			mainImage.setImageResource(R.drawable.dan_angel_pic);
			studentName.setText("Name: Dan Schepers");
			studentCourse.setText("Course: N/A");
			break;
		default:
			// should not get here
		}
	}
}
