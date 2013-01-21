package edu.rosehulman.namefacerecognizer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.rosehulman.namefacerecognizer.R;

public class LoginActivity extends Activity implements OnClickListener {
	
	private Button loginButton;
	private EditText nameField;
	private EditText passField;
	private Button exitButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.submit_button);
        loginButton.setOnClickListener(this);
        nameField = (EditText) findViewById(R.id.username_editText);
        passField = (EditText) findViewById(R.id.password_editText);
        exitButton = (Button) findViewById(R.id.cancel_button);
        exitButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit_button:
			if (nameField.getText().toString().equals(getString(R.string.username)) && passField.getText().toString().equals(getString(R.string.password))) {
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.cancel_button:
			this.finish();
			break;
		default:
			// should not get here
		}
	}
}
