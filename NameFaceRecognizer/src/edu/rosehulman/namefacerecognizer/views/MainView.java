package edu.rosehulman.namefacerecognizer.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import edu.rosehulman.namefacerecognizer.R;

public class MainView extends RelativeLayout {

	/**
	 * An interface to send events from the view to the controller
	 */
	public static interface ViewListener {
		public void onPullStudentsRequested();
		public void onReviewRequested();
		public void onQuizRequested();
		public void onExitRequested();
	}

	private Button reviewButton;
	private Button quizButton;
	private Button downloadButton;
	private Button exitButton;

	/**
	 * The listener reference for sending events
	 */
	private ViewListener viewListener;

	public MainView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = View.inflate(context, R.layout.activity_main, null);
		addView(view);
		reviewButton = (Button) findViewById(R.id.review_button);
		reviewButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Tell the controller to deal with it
				viewListener.onReviewRequested();

			}
		});

		quizButton = (Button) findViewById(R.id.quiz_button);
		quizButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Tell the controller to deal with it
				viewListener.onQuizRequested();

			}
		});

		downloadButton = (Button) findViewById(R.id.download_button);
		downloadButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Tell the controller to deal with it
				viewListener.onPullStudentsRequested();

			}
		});

		exitButton = (Button) findViewById(R.id.exit_button);
		exitButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Tell the controller to deal with it
				viewListener.onExitRequested();
			}
		});		

	}

	public void setViewListener(ViewListener viewListener) {
		this.viewListener = viewListener;
	}

}
