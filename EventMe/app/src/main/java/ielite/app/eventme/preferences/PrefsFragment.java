package ielite.app.eventme.preferences;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import ielite.app.eventme.R;

/**
 * Created by Suraj on 22-03-2016.
 */
public class PrefsFragment extends PreferenceFragment {
    private static final String TAG = PrefsFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

    }
}

