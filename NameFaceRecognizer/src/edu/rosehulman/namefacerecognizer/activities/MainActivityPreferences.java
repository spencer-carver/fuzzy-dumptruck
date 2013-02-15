package edu.rosehulman.namefacerecognizer.activities;

import edu.rosehulman.namefacerecognizer.R;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainActivityPreferences extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		addPreferencesFromResource(R.xml.activity_main_preferences);
		updatePreferences();

		IntegerPrefListener listener = new IntegerPrefListener();
		findPreference(getString(R.string.number_pref))
				.setOnPreferenceChangeListener(listener);
		findPreference(getString(R.string.number_pref)).setSummary(
				(CharSequence) prefs.getString(getString(R.string.number_pref),
						"6"));
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		if (preference.getKey().equals(getString(R.string.key_pref))) {
			setResult(RESULT_OK, getIntent());
			finish();
		}

		if (preference.getKey().equals(getString(R.string.key_reload))) {
			setResult(RESULT_FIRST_USER, getIntent());
			finish();
		}

		if (preference.getKey().equals(getString(R.string.number_pref))) {

		}

		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

	private void updatePreferences() {
	}

	private class IntegerPrefListener implements OnPreferenceChangeListener {

		public boolean onPreferenceChange(Preference preference, Object newValue) {
			try {
				Integer.parseInt((String) newValue);
			} catch (Exception e) {
				Toast.makeText(MainActivityPreferences.this,
						getString(R.string.invalid_pref), Toast.LENGTH_SHORT)
						.show();
				return false;
			}
			preference.setSummary((CharSequence) newValue);
			return true;
		}

	}
}