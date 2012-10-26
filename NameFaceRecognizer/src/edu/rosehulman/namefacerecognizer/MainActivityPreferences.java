package edu.rosehulman.namefacerecognizer;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

@SuppressWarnings("deprecation")
public class MainActivityPreferences extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.activity_main_preferences);
		updatePreferences();
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		if (preference.getKey().equals(getString(R.string.key_pref))) {
			setResult(RESULT_OK, getIntent());
			finish();
		}

		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

	private void updatePreferences() {
	}
}