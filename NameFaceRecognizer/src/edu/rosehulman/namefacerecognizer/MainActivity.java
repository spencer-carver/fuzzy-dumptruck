package edu.rosehulman.namefacerecognizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	
	private Button reviewButton;
	private Button quizButton;
	private Button downloadButton;
	private Button exitButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reviewButton = (Button) findViewById(R.id.review_button);
        reviewButton.setOnClickListener(this);
        quizButton = (Button) findViewById(R.id.quiz_button);
        quizButton.setOnClickListener(this);
        downloadButton = (Button) findViewById(R.id.download_button);
        downloadButton.setOnClickListener(this);
        exitButton = (Button) findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.review_button:
			intent = new Intent(this, ReviewActivity.class);
			startActivity(intent);
			break;
		case R.id.quiz_button:
			intent = new Intent(this, QuizActivity.class);
			startActivity(intent);
			break;
		case R.id.download_button:
			break;
		case R.id.exit_button:
			finish();
			break;
		default:
			//should not get here
		}
		
	}
}