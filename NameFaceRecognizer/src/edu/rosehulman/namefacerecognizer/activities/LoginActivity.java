package edu.rosehulman.namefacerecognizer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.widget.Toast;
import edu.rosehulman.namefacerecognizer.R;
import edu.rosehulman.namefacerecognizer.exceptions.InternetConnectivityException;
import edu.rosehulman.namefacerecognizer.services.AuthenticationService;
import edu.rosehulman.namefacerecognizer.views.LoginView;
import edu.rosehulman.namefacerecognizer.views.LoginView.ViewListener;

public class LoginActivity extends Activity implements ViewListener {

	private LoginView loginView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		loginView = new LoginView(this, null);
		loginView.setViewListener(this);
		setContentView(loginView);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	public void onLoginRequested(String username, String password) {
		// ONLY FOR TESTING
		//		if (username.equals("admin")) {
		//			Intent intent = new Intent(this, MainActivity.class);
		//			intent.putExtra("username", password);
		//			startActivity(intent);
		//		}
		//		else {
		try {
			boolean authenticated = AuthenticationService.getInstance().authenticate(username, password);
			if (authenticated) {
				Intent intent = new Intent(this, MainActivity.class);
				intent.putExtra("username", username);
				startActivity(intent);
			} else {
				Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
			}
		} catch (InternetConnectivityException e) {
			e.printStackTrace();
			Toast.makeText(this, "Internet Connectivity issues. Please try again later.", Toast.LENGTH_SHORT).show();
		}
		//		}
	}

	public void onExitRequested() {
		this.finish();
	}
}
