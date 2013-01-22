package edu.rosehulman.namefacerecognizer.views;

import edu.rosehulman.namefacerecognizer.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class LoginView extends RelativeLayout {
	
	private Button loginButton;
	private EditText nameField;
	private EditText passField;
	private Button exitButton;
	private ViewListener viewListener;
	
	/**
	 * An interface to send events from the view to the controller
	 */
	public static interface ViewListener {
		public void onLoginRequested(String username, String password);
		public void onExitRequested();
	}
	
	public LoginView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = View.inflate(context, R.layout.activity_login, null);
		this.addView(view);

		nameField = (EditText) view.findViewById(R.id.username_editText);
		passField = (EditText) view.findViewById(R.id.password_editText);
        
		loginButton = (Button) view.findViewById(R.id.submit_button);
        loginButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				viewListener.onLoginRequested(nameField.getText().toString(), passField.getText().toString());
			}
		});
        
        exitButton = (Button) view.findViewById(R.id.cancel_button);
        exitButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				viewListener.onExitRequested();
			}
		});
	}

	public void setViewListener(ViewListener listener) {
		this.viewListener = listener;
	}
}
